<#import "/spring.ftl" as s/>
<#import "../macro/ui.ftl" as ui>

<#global layout_title><@s.message "knight.list"/></#global>
<#global layout_body>
<div class="row-fluid">
    <div class="span4 offset7 ">
        <form class="form-search" action="display-list.htm" method="get">
            <div class="input-append">
                <input type="text" name="keyword" value="${RequestParameters.keyword!}" class="span10 search-query">
                <button type="submit" class="btn"><@s.message "search"/></button>
            </div>
        </form>
    </div>
    <div class="span1">
        <a class="btn" href="edit-view"><@s.message "add"/></a>
    </div>
</div>


<table class="table table-hover">
    <thead>
    <tr>
        <th><@s.message 'name'/></th>
        <th><@s.message 'description'/></th>
        <th><@s.message 'status'/></th>
        <th><@s.message 'operation'/></th>
    </tr>
    </thead>
    <tbody>
        <#list  knights as knight>
        <tr>
            <td>${knight.name!}</td>
            <td>${knight.description}</td>
            <td><@s.message 'knight.status.'+knight.status.id/></td>
            <td>
                <a class="btn btn-link" href="edit-view?number=${knight.number?c}"><@s.message "update"/></a>
                <a class="btn btn-link" href="display-delete?number=${knight.number?c}"><@s.message "delete"/></a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
    <@ui.pageFooter/>
</#global>
<#include "../layout/single-panel.ftl">
