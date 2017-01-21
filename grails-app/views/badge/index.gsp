<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'badge.label', default: 'Badge')}" />
        <title><g:message code="home.nav.badges" /></title>
    </head>
    <body class="container-fluid">
        <h1 class="col-sm-offset-2 page-title"><g:message code="home.nav.badges"/></h1>
        <tr/>
        <g:each in="${ badgeList }" var="b">
            <tr class="row">
                <td>
                    <g:render template="/badge/displayBadge" model="['b':b]" />
                </td>
            </tr>
        </g:each>
    </body>
</html>