import handlers.RouterHandler
import handlers.TestHandler
import ratpack.form.Form
import ratpack.handling.Handler

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

    handler("persons") {
      byMethod {
        post {
          Form form = parse(Form)
          render form.toMapString()
        }
      }
    }

    get("redirect"){
      redirect("persons/1?name=redirected")

    }

    get('first', new TestHandler())

    prefix("customHandler") {
       get('first', new TestHandler())
    }

    get('router', new RouterHandler())



    assets "public"
  }
}
