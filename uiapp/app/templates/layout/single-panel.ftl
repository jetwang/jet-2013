<#import "../macro/ui.ftl" as ui>
<#compress>
<!DOCTYPE html>
<html>
<head>
    <title>${.globals.layout_title!}</title>
    <#include "/layout/inc/common_header.ftl">
${.globals.layout_head!}
</head>
<body>
<div class="center_container">
    <div class="center_container_right_container">
        <div class="index_right_top">
            <a href="${reqContext.buildUrl({"locale":"en_US"})}">English</a>
            <a href="${reqContext.buildUrl({"locale":"zh_CN"})}">简体中文</a>
            <a href="/statics/list-show" target="_blank">SOAP List</a>
            <a href="/rs/application.wadl" target="_blank">REST WADL</a>
        </div>

        <div class="index_right_bg">
        <#--<#include "../macro/error-message.ftl"/>-->
            <#if _user??>
                <span>Hello, ${_user.email}</span>
                &nbsp;
                <a href="../user/logout"><@s.message "logout"/></a>
            </#if>
            <div class="H1">
            ${.globals.layout_title!}
            </div>
        ${.globals.layout_body!}
        </div>
        <div class="index_right_bottom"></div>
    </div>
    <div class="index_solid"></div>
</div>
</body>
    <#include "/layout/inc/common_footer.ftl">
</html>
</#compress>