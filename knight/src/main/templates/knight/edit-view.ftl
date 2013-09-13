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
<form action="edit-submit.htm" method="post" class="form-horizontal" target="hiddenContainer">
    <@s.formHiddenInput path="form.knightId"/>
    <input name="number" value="${form.number!?c}" type="hidden">

    <@ui.errorMessage "form"/>

    <div class="control-group">
        <label for="name" class="control-label"><@s.message "name"/>: </label>

        <div class="controls">
            <@s.formInput path="form.name"  attributes="class=\"text_input\""/>
                <@ui.error "name"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="description"><@s.message "description"/>: </label>

        <div class="controls">
            <@s.formInput path="form.description"  attributes="class=\"text_input\""/>
            <@ui.error "description"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="statusId"><@s.message "status"/>: </label>

        <div class="controls">
            <@s.formSingleSelect "form.statusId" statusOptions/>
        <@ui.error "statusId"/>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn btn-primary"><@s.message "submit"/></button>
            <a href="display-list" class="btn btn-link"><@s.message "back"/></a>
        </div>
    </div>

</form>

</#global>
<#include "/layout/single-panel.ftl">
