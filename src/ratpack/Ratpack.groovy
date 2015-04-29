import groovy.json.JsonBuilder
import ratpack.form.Form
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.groovy.template.TextTemplateModule

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
  }

  handlers {
    handler("persons/:id"){
      byMethod {
        get {
          render "I am person with id $pathTokens.id and your name is "+request.queryParams.name
        }

        put {
          Form form = parse(Form)
          render form.toMapString()
        }
      }
    }

    handler("persons"){
      Form form = parse(Form)
      render form.toMapString()
    }


    assets "public"
  }
}
