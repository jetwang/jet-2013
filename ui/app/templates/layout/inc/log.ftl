<#if request.getParameter("debugAll") =="true">
<@s.hidden value="%{@com.wind.restapp.common.framework.util.StringUtils@htmlEncode(@RequestScopeAppender@builder().toString())}" id="debugLoggingInfo"/>
<script type="text/javascript">
    var newWindow = window.open('', 'debugLoggingWindow', 'width=300,height=100');
    newWindow.document.write("<p>"+$byId("debugLoggingInfo").value+"</p>");
</script>
</#if>
