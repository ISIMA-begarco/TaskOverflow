package tasko

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class StatsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Stats.list(params), model:[statsCount: Stats.count()]
    }

    def show(Stats stats) {
        respond stats
    }

    def create() {
        respond new Stats(params)
    }

    @Transactional
    def save(Stats stats) {
        if (stats == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (stats.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stats.errors, view:'create'
            return
        }

        stats.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'stats.label', default: 'Stats'), stats.id])
                redirect stats
            }
            '*' { respond stats, [status: CREATED] }
        }
    }

    def edit(Stats stats) {
        respond stats
    }

    @Transactional
    def update(Stats stats) {
        if (stats == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (stats.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stats.errors, view:'edit'
            return
        }

        stats.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'stats.label', default: 'Stats'), stats.id])
                redirect stats
            }
            '*'{ respond stats, [status: OK] }
        }
    }

    @Transactional
    def delete(Stats stats) {

        if (stats == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        stats.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'stats.label', default: 'Stats'), stats.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'stats.label', default: 'Stats'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
