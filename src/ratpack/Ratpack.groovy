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
      get("simpleAsync") {
          blocking {
              sleep(3000)
              return "blockin operation result"
          }.onError{ e->
            response.status 500
            response.send(e.message)
          }
          .then { result ->
              render result
          }
      }


      get("asyncWithPromise") {
          promise { fulfiller ->
              Thread.start {
                  sleep 1000
                  fulfiller.success(["Hello world"])
              }
          }.then {def data ->
              render(data.toString())
          }
          println "2"
      }

      get ("multipleAsyncs") {

          def l = []
          promise { f ->
              Thread.start {
                  sleep 3000
                  f.success("1")
              }
          }.then {
              l << it
          }

          promise { f ->
              Thread.start {
                  sleep 2000
                  f.success("2")
              }
          }.then {
              l << it
          }

          promise { f ->
              Thread.start {
                  sleep 1000
                  f.success("3")
              }
          }.then {
              l << it
              render l.join(",")
          }

      }



      assets "public"
  }
}
