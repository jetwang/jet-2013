<#import "/spring.ftl" as s/>
<#import "../macro/ui.ftl" as ui>

<#global layout_title>display web services</#global>
<#global layout_body>
<span class="label label-info">SOAP Web Services</span>
<table class="table table-hover">
    <thead>
    <tr>
        <td><@s.message 'name'/></td>
        <td><@s.message 'url'/></td>
        <td><@s.message 'wsdl'/></td>
        <td><@s.message 'description'/></td>
    </tr>
    </thead>
    <#list form.soapApplication.soapWebServiceInfos as soapWebServiceInfo >
        <tr>
            <td>${soapWebServiceInfo.name!}</td>
            <td><a href="${soapWebServiceInfo.url}" target="_blank">${soapWebServiceInfo.url}</a></td>
            <td><a href="${soapWebServiceInfo.wsdlLocation}" target="_blank">${soapWebServiceInfo.wsdlLocation}</a></td>
            <td>${soapWebServiceInfo.description!}</td>
        </tr>
    </#list>
</table>
<span class="label label-info">REST Web Services</span>
<table class="table table-hover">
    <thead>
    <tr>
        <td><@s.message 'path'/></td>
        <td><@s.message 'method'/></td>
        <td><@s.message 'request'/></td>
        <td><@s.message 'response'/></td>
    </tr>
    </thead>
    <tbody>
        <#list form.restApplication.resources as baseResource>
            <#list baseResource.resource as resource>
                <#list resource.methodOrResource as methodOrResource>
                    <#list methodOrResource.methodOrResource as method>
                    <tr>
                        <td><a target="_blank" href="${baseResource.base}${resource.path}${methodOrResource.path!}">${baseResource.base}${resource.path}${methodOrResource.path!}</a></td>
                        <td>${method.name}</td>
                        <td>
                            <#if method.request??>
                                <#list method.request.representation as representation>
                                ${representation.element.localPart} : ${representation.mediaType}<br>
                                </#list>
                            </#if>
                        </td>
                        <td>
                            <#list method.response as response>
                                <#list response.representation as representation>
                                    <#if representation.element??>${representation.element.localPart!} : </#if>${representation.mediaType}<br>
                                </#list>
                            </#list>
                        </td>
                    </tr>
                    </#list>
                </#list>
            </#list>
        </#list>      </tbody>
</table>
</#global>
<#include "../layout/single-panel.ftl">
