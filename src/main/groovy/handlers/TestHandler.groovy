package handlers


import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

class TestHandler extends GroovyHandler{

    @Override
    protected void handle(GroovyContext context){
        context.response.send("I am you test handler")
//        context.with{
//            get(){
//                response.send("Hello handler")
//            }
//        }
    }
}
