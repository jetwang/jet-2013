<#import "../macro/ui.ftl" as ui>
<#compress>
<!DOCTYPE html>

<head>
    <title>${.globals.layout_title!}</title>
    <#include "/layout/inc/common_header.ftl">
${.globals.layout_head!}
</head>


<div class="container">
    <div class="row">
        <div class="span8">
            <div class="navbar">
                <div class="navbar-inner">
                    <ul class="nav">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#z">
                                <@s.message "language"/><b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="${reqContext.buildUrl({"locale":"en_US"})}">English</a></li>
                                <li>
                                    <a href="${reqContext.buildUrl({"locale":"zh_CN"})}">简体中文</a></li>
                                <li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#z">Web Service<b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="/rs/webservice/soap" target="_service">SOAP List</a></li>
                                <li>
                                    <a href="/rs/application.wadl" target="_service">REST WADL</a></li>
                                <li>
                                    <a href="/console/web-service-list-all" target="_service">Services Table</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="/knight/display-list" target="_knight"><@s.message 'knight'/></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <#if _user??>
            <div class="span3">
                <span class="label ">Hello, <i class="icon-user"></i> ${_user.email}</span>
            </div>
            <div class="span1">
                <a class="btn btn-link" href="../user/logout"><@s.message "logout"/></a>
            </div>
        </#if>
    </div>
    <legend>
    ${.globals.layout_title!}
    </legend>
${.globals.layout_body!}
</div>
    <#include "/layout/inc/common_footer.ftl">

</#compress>