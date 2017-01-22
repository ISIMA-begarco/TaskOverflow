<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'comMessage.label', default: 'ComMessage')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body class="container-fluid">
    <h1 class="col-sm-offset-2 page-title"><g:link controller="question" action="show" id="${questionId}"><g:message code="other.back"/></g:link> > <g:message code="other.new"/> </h1>
        <g:form controller="answer" action="addAnswer" method="post">
            <g:hiddenField name="questionId" value="${questionId}" />
            <g:hiddenField name="messageId" value="${messageId}" />
            <g:textArea name="text" value="" />
            <g:submitButton name="addAnwser" value="Submit" />
        </g:form>
    </body>
</html>
