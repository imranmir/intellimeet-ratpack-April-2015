import groovy.json.JsonBuilder
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.groovy.template.TextTemplateModule
import ratpack.session.SessionModule
import ratpack.session.clientside.ClientSideSessionsModule
import ratpack.session.store.SessionStorage

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    add MarkupTemplateModule
    add(TextTemplateModule) /*{ TextTemplateModule.Config config -> config.staticallyCompile = true }*/
      add SessionModule
      add ClientSideSessionsModule

  }

  handlers {
      get('setSession'){
        request.get(SessionStorage).put('name',request.queryParams.name)
      }

    get("getSession"){
      render request.get(SessionStorage.class).get('name');
    }

    get("config"){
      File cfg = new File('./config', 'Config.groovy')
      config = new ConfigSlurper().parse(cfg.text).app
      render new JsonBuilder(config).toPrettyString()
    }


      assets "public"
  }
}

