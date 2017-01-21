<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'badge.label', default: 'Badge')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body class="container-fluid">
    <h1 class="col-sm-offset-2 page-title"><g:link controller="badge" action="index"><g:message code="home.nav.badges"/></g:link> > ${this.badge.label}</h1>
        <tr/>
            <tr class="row">
                <td>
                    <g:render template="/badge/displayBadge" model="['b':this.badge]" />
                </td>
            </tr>
    </body>
</html>
