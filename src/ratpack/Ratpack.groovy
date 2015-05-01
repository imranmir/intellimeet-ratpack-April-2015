import ratpack.exec.ExecControl
import ratpack.exec.Promise
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.groovy.template.TextTemplateModule
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    add MarkupTemplateModule
    add(TextTemplateModule) /*{ TextTemplateModule.Config config -> config.staticallyCompile = true }*/
    add(new JacksonModule())
  }

  handlers {

      get('readFile') {
          blocking {
              new File("/Users/apple/.bash_profile").text.toUpperCase()
          } then {
              render it
          }
      }
      get('readFileImproved') {
          blocking {
              new File("/Users/apple/.bash_profile").text
          } then {
              render it.toUpperCase()
          }
      }


      get ('useCustomMethod'){
          getFileTextUpper(context).then {
              render it
          }
      }
      get ('useCustomMethodUsingFlatMap'){
          getFileTextUpperUsingFlatMapMap(context).then {
              render it
          }
      }

      assets "public"
  }
}

Promise<String> getFileTextUpper(ExecControl control) {
    control.blocking {
        new File("/Users/apple/.bash_profile").text
    }.map {
        it.toUpperCase()
    }
}

Promise<String> getFileTextUpperUsingFlatMapMap(ExecControl control) {
    control.blocking {
        new File("/Users/apple/.bash_profile").text
    }.flatMap { s1 ->
        control.blocking {
            new File("/Users/apple/.aliases").text
        }.map { s2 ->
            s1 + s2
        }
    }
}