<header class="color_bg_lightblue">
    <a id="home_link" class="button hover_medblue font_title color_darkblue" href="${root}">steambeat</a>

    <div id="id_panel">
    <#if identity?has_content>
        <p>Hello ${identity} <a href="javascript:void(0);" id="logout">logout</a></p>
    <#else>
        <p>login or <a href="${root}/signup">create account</a></p>
    </#if>
    </div>

</header>