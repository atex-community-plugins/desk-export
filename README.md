# Desk-Export 
Enables exporting polopoly content into desk
# Setup
In order to get this plugin to work it is required to follow the following steps:
* Import the plugin from this repository and add it into your plugins directory.
* Add plugin to project level pom.xml
```xml
<module>plugins/desk-export</module>
```
* Add a dependency on the plugin to project pom.xml
```xml
<dependency>
	<groupId>com.atex.plugins</groupId>
	<artifactId>desk-export</artifactId>
	<version>0.1-SNAPSHOT</version>
</dependency>

``` 

* Modify your article/image template and add the following
```xml
<field name="deskExport" input-template="com.atex.plugins.deskexport.ExportButton" label=""/>
```
The button Export to Desk should now be a part of the template.

# Note

This plugin uses one of the desk endpoints for engagement.
It is required to setup mappings between the two content types e.g.

```xml
<mapping>
	<class-a>com.atex.standard.article.ArticleBean</class-a>
    <class-b>com.atex.nosql.article.ArticleBean</class-b>
    <field custom-converter="com.atex.onecms.app.dam.mapping.PlainTextConverter">
    	<a>headline</a>
    	<b>headline.text</b>
    </field>
    <field custom-converter="com.atex.onecms.app.dam.mapping.PlainTextConverter">
   	    <a>lead</a>
   	    <b>lead.text</b>
    </field>
    <field>
    	<a>body</a>
        <b>body.text</b>
    </field>
    <field custom-converter="com.atex.onecms.app.dam.mapping.PlainTextConverter">
    	<a>auteur</a>
        <b>auteur.text</b>
    </field>
    <field-exclude>
    	<a>images</a>
        <b>images</b>
    </field-exclude>
   	<field-exclude>
    	<a>ogImages</a>
        <b>ogImages</b>
    </field-exclude>
    <field-exclude>
    	<a>resources</a>
        <b>resources</b>
    </field-exclude>
</mapping>
```

