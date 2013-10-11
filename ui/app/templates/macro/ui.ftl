<#import "/spring.ftl" as s/>

<#macro error field>
<span class="error_message" id="error_span_for_${field}"
      style="color:red">
    <#if userTip??&&userTip.hasFieldErrors(field)>
    <@s.messageArgs userTip.getFieldErrors(field).get(0)!.error.textKey userTip.getFieldErrors(field).get(0)!.error.textArgs/>
    </#if>
</span>
</#macro>

<#macro pageFooter name="page" pageNumberFieldName="pageNumber">
    <#assign page=Request[name]>
    <#if page??&& (page.rowCount>page.pageSize)>
    <div class="more_item_div">
        <#if (page.pageNumber > 1)>
            <a class="first active" href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:1})}',this);"><@s.message "First"/></a>
            <a class="previous active" href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:page.pageNumber - 1})}',this);"><@s.message "Previous"/></a>
        <#else>
            <span class="first" id="firstPage">First</span>
            <span class="prev" id="previousPage">Previous</span>
        </#if>

        <#if page.pageNumber < page.pageCount>
            <a class="next active" href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:page.pageNumber + 1})}',this);"><@s.message "Next"/></a>
            <a class="last active" href="#z" onclick="y.loadUrl('${reqContext.buildUrl({pageNumberFieldName:page.pageCount})}',this);"><@s.message "Last"/></a>
        <#else>
            <span class="next" id="nextPage">Next</span>
            <span class="last" id="lastPage">Last</span>
        </#if>
    </div>
    </#if>
</#macro>

<#macro errorMessage path="">
    <#assign errors=reqContext.getErrors(path)!/>
<div class="error_panel">
    <#if errors??&&errors.globalErrors??>
        <#list errors.globalErrors as error>
            <p class="error_message">${reqContext.getMessage(error)}</p>
        </#list>
        <script type="text/javascript">
            $(function () {
                <#list errors.fieldErrors as fieldError>
                    y.highLight("${fieldError.field!}");
                    y.addMessage('${reqContext.getMessage(fieldError)}', "${fieldError.field!}");
                </#list>
            });
        </script>
    </#if>
</div>
<iframe style="display:none" name="hiddenContainer"></iframe>
</#macro>