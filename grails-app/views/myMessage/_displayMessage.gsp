<div class="panel ${type}">
    <div class="panel-body ${style}">
        <div class="${style}">
            <div class="col-xs-3">
                <div class="col-xs-12 text-center">
                    <a href="#" class="btn btn-success btn-lg">
                        <span class="glyphicon glyphicon-chevron-up"></span>
                    </a>
                </div>
                <div class="col-xs-12 text-center"><h2>${a.value}</h2></div>
                <div class="col-xs-12 text-center">
                    <a href="#" class="btn btn-danger btn-lg">
                        <span class="glyphicon glyphicon-chevron-down"></span>
                    </a>
                </div>
            </div>
            <div class="col-xs-9 text-justify">${a.content}</div>
            <sec:ifLoggedIn>
                <div class="col-xs-12 text-justify coms"><g:message code="message.addcom"/></div>
            </sec:ifLoggedIn>
            <g:each in="${a.coms}" var="c">
                <g:render template="/comMessage/displayCom" model="['com':c]"/>
            </g:each>
        </div>
    </div>
    <div class="panel-footer text-right"><g:link controller="user" action="show" id="${a.user.id}">${a.user.username}</g:link>, ${a.date.format('dd/MM/yyyy HH:mm')}</div>
</div>