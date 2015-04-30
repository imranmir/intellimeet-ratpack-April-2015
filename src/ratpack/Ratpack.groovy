import groovy.json.JsonBuilder
import ratpack.form.Form
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.groovy.template.TextTemplateModule

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack
import ratpack.jackson.JacksonModule
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    add MarkupTemplateModule
    add(TextTemplateModule) /*{ TextTemplateModule.Config config -> config.staticallyCompile = true }*/
    add(new JacksonModule())
  }

  handlers {
    get {
      render groovyMarkupTemplate("index.gtpl", title: "My Ratpack App")
    }

    get("renderHtml"){
      render groovyTemplate("first.html", title: "My Ratpack App")
    }

    get('renderSimpleString') {
      render "Hello. I have been rendered as a String"
    }

    get("renderJSON"){
      Map map = [firstName: 'imran', lastName: 'Mir']
      render new JsonBuilder(map).toPrettyString()
    }

    get("renderJSONUsingJackson"){
      Map map = [firstName: 'imran', lastName: 'Mir']
      render json(map)

    }

    post ("personForm"){
      Form form = parse(Form)
      render form.toMapString()
    }


    assets "public"
  }
}
