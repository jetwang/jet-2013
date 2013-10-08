<#import "/macro/ui.ftl" as ui>
<#import "/spring.ftl" as s/>
<script type="text/javascript">
    var affectWin = top;
    if (affectWin != this) {
        affectWin.y.resetMessage();
        affectWin.y.restHighLight();
        affectWin.y.addMessage('<@s.message "something.error.with.current.request"/>');
    }
</script>
<#global layout_title><@s.message "current.page.unavailable"/></#global>
<#global layout_body>
<p class="txt_title"><@s.message "current.page.unavailable.detail.message"/></p>
<@ui.errorMessage ""/>

<p><@s.message "current.page.unavailable.to.do"/><a href="javascript: history.back();" class="link_blue"><@s.message "back"/></a>
</p>

<p>
    <a target="_top" href="../" class="link_green"><@s.message "go.to.home.page"/></a>
</p>

<p>
    <@s.message "help.yourselves.if.happen.again"/>
</p>
</#global>
<#include "/layout/single-panel.ftl">
