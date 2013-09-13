<#import "../macro/ui.ftl" as ui>
<#import "/spring.ftl" as s/>
<#global layout_title>
    <@s.message "login"/>
</#global>
<#global layout_body>
<div>
    <form action="login" method="post" class="form-horizontal" target="hiddenContainer">
        <input type="hidden" name="from" value="${RequestParameters.from!}"/>


            <@ui.errorMessage "form"/>

        <div class="control-group">
            <label class="control-label" for="form.email"><@s.message "email"/>: </label>

            <div class="controls">
                <@s.formInput path="form.email"  attributes="class=\"text_input\" placeholder=\"Email\""/>
                <@ui.error "email"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="form.password"><@s.message "password"/>: </label>

            <div class="controls">
                <@s.formPasswordInput path="form.password"  attributes="class=\"text_input\" placeholder=\"Password\""/>
                <@ui.error "password"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-primary"><@s.message "submit"/></button>
                <a href="#z" onclick="history.back(); return false;" class="btn btn-link"><@s.message "cancel"/></a>
            </div>
        </div>

    </form>
</div>
</#global>
<#include "/layout/single-panel.ftl">
