package ovh.garcon.tasko

class LogoutController {

    def index() {
        session.invalidate()
        redirect controller:"question", action:"index", method:"GET"
    }
}
