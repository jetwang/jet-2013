<#import "/spring.ftl" as s/>
<#import "../macro/ui.ftl" as ui>

<#global layout_title>display knight</#global>
<#global layout_body>
<table width="100%">
    <tr>
        <td colspan="3"><a href="edit-view"><@s.message "add"/></a></td>
    </tr>
    <tr>
        <td colspan="3">
            <form action="display-list.htm" method="get">
                <@s.message "search"/>: <input type="text" name="keyword" value="${RequestParameters.keyword!}">
            </form>
            <hr/>
        </td>
    </tr>
    <tr>
        <td><@s.message 'name'/></td>
        <td><@s.message 'description'/></td>
        <td><@s.message 'status'/></td>
        <td><@s.message 'operation'/></td>
    </tr>
    <#list  knights as knight>
        <tr>
            <td>${knight.name!}</td>
            <td>${knight.description}</td>
            <td><@s.message knight.status.title+'.message'/></td>
            <td>
                <a href="edit-view?number=${knight.number?c}"><@s.message "update"/></a>|
                <a href="display-delete?number=${knight.number?c}"><@s.message "delete"/></a>
            </td>
        </tr>
    </#list>
</table>
    <@ui.pageFooter/>
</#global>
<#include "../layout/single-panel.ftl">
