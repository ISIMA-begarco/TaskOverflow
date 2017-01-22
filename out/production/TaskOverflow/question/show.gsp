<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
    <title>${this.question.title}</title>
    </head>
    <body class="container-fluid">
        <div class="col-sm-offset-2 col-sm-8 col-xs-offset-1 col-xs-10">
            <h1 class="page-title">${this.question.title}<g:if test="${sec.username()==this.question.user.username}">
                <g:link class="edit" action="edit" resource="${this.question}"><g:message code="default.button.edit.label" default="Edit" /></g:link></g:if></h1>
            <h2><g:message code="home.nav.tags"/>: <g:each in="${this.question.tags}" var="t">
                <g:link controller="tag" action="show" id="${t.id}">${t.label}</g:link>
            </g:each></h2>

            <g:render template="/questionMessage/displayQuestion" model="['q':this.question]" />

            <g:each in="${this.question.answers}" var="a">
                <g:render template="/answerMessage/displayAnswer" model="['a':a]" />
            </g:each>
        </div>
    </body>
                    <g:link class="edit" action="edit" resource="${this.question}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
</html>
