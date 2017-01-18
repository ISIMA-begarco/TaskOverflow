package tasko

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class AdministratorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Administrator.list(params), model:[administratorCount: Administrator.count()]
    }

    def show(Administrator administrator) {
        respond administrator
    }

    def create() {
        respond new Administrator(params)
    }

    @Transactional
    def save(Administrator administrator) {
        if (administrator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (administrator.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond administrator.errors, view:'create'
            return
        }

        administrator.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'administrator.label', default: 'Administrator'), administrator.id])
                redirect administrator
            }
            '*' { respond administrator, [status: CREATED] }
        }
    }

    def edit(Administrator administrator) {
        respond administrator
    }

    @Transactional
    def update(Administrator administrator) {
        if (administrator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (administrator.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond administrator.errors, view:'edit'
            return
        }

        administrator.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'administrator.label', default: 'Administrator'), administrator.id])
                redirect administrator
            }
            '*'{ respond administrator, [status: OK] }
        }
    }

    @Transactional
    def delete(Administrator administrator) {

        if (administrator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        administrator.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'administrator.label', default: 'Administrator'), administrator.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'administrator.label', default: 'Administrator'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
