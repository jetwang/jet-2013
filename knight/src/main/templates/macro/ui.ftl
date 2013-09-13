<#import "/spring.ftl" as s/>

<#macro error field>
<span class="help-inline" id="error_span_for_${field}">
    <#if userTip??&&userTip.hasFieldErrors(field)>
    <@s.messageArgs userTip.getFieldErrors(field).get(0)!.error.textKey userTip.getFieldErrors(field).get(0)!.error.textArgs/>
    </#if>
</span>
</#macro>

<#macro pageFooter name="page" pageNumberFieldName="pageNumber">
    <#assign page=Request[name]>
    <#if page??&& (page.rowCount>page.pageSize)>
    <div class="pagination pull-right">
        <ul>
            <#if (page.pageNumber > 1)>
                <li>
                    <a href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:1})}',this);"><@s.message "First"/></a></li>
                <li>
                    <a href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:page.pageNumber - 1})}',this);"><@s.message "Previous"/></a></li>
            <#else>
                <li>
                    <span class="first" id="firstPage">First</span></li>
                <li>
                    <span class="prev" id="previousPage">Previous</span></li>
            </#if>

            <#if page.pageNumber < page.pageCount>
                <li>
                    <a href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:page.pageNumber + 1})}',this);"><@s.message "Next"/></a></li>
                <li>
                    <a href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:page.pageCount})}',this);"><@s.message "Last"/></a></li>
            <#else>
                <li>
                    <span id="nextPage">Next</span></li>
                <li>
                    <span id="lastPage">Last</span></li>
            </#if>
        </ul>
    </div>
    </#if>
</#macro>

<#macro errorMessage path="">
    <#assign errors=reqContext.getErrors(path)!/>
    <#if errors??&&errors.globalErrors??&&(errors.globalErrors?size>0)>
    <div id="messageContainer" class="alert">
        <#list errors.globalErrors as error>
            <p>${reqContext.getMessage(error)}</p>
        </#list>
    </div>
    <#else>
    <div id="messageContainer"></div>
    </#if>

<iframe style="display:none" name="hiddenContainer"></iframe>
</#macro>