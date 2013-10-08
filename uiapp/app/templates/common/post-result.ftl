<#import "/macro/ui.ftl" as ui>
<#import "/spring.ftl" as s/>

<script type="text/javascript">
    var affectWin = top;
    if (!affectWin) {
        affectWin = this;
    }
    affectWin.y.resetMessage();
    affectWin.y.resetHighLight();
    <#if errors??&&errors.hasErrors()>
        <#list errors.globalErrors as error>
        affectWin.y.addMessage('${reqContext.getMessage(error)}');
        </#list>
        <#list errors.fieldErrors as fieldError>
        affectWin.y.highLight("${fieldError.field!}");
        affectWin.y.addMessage('${reqContext.getMessage(fieldError)}', "${fieldError.field!}");
        </#list>
    <#elseif redirectUrl?has_content>
    affectWin.y.showWaiting();
    affectWin.$(document).ready(function () {
        affectWin.location = "${redirectUrl}";
    });
    </#if>
    <#if successfulMessage?has_content>
    affectWin.y.prompt("${successfulMessage}");
    </#if>
    affectWin.y.hideWaiting();
</script>