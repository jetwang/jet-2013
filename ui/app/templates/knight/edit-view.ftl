<#import "../macro/ui.ftl" as ui>
<#import "/spring.ftl" as s/>
<#global layout_title>
    <#if form.number==0>
        <@s.message "add"/>&nbsp;<@s.message "knight"/>

    <#else>
        <@s.message "update"/>&nbsp;<@s.message "knight"/>
    </#if>

</#global>
<#global layout_body>
<div>
    <form action="edit-submit.htm" method="post" target="hiddenContainer">
        <@s.formHiddenInput path="form.knightId"/>
        <input name="number" value="${form.number!?c}" type="hidden">
        <fieldset>
            <legend>
                <@ui.errorMessage "form"/>
            </legend>
            <div>
                <label><@s.message "name"/>: </label><@ui.error "name"/>
            </div>
            <div>
                <@s.formInput path="form.name"  attributes="class=\"text_input\""/>
            </div>

            <div>
                <label><@s.message "description"/>: </label><@ui.error "description"/>
            </div>
            <div>
                <@s.formInput path="form.description"  attributes="class=\"text_input\""/>
            </div>

            <div>
                <label><@s.message "status"/>: </label><@ui.error "status"/>
            </div>
            <div>
                <@s.formSingleSelect "form.statusId" statusOptions/>
            </div>

            <div>
                <button type="submit"><@s.message "submit"/></button>
                <a href="#z" onclick="history.back(); return false;"><@s.message "cancel"/></a>
            </div>
        </fieldset>
    </form>
</div>
</#global>
<#include "/layout/single-panel.ftl">
