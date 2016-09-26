<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='xml' indent='yes'/>

<!-- Receives the id of the menu being rendered. -->
<xsl:param name="MinSalary" />

    <xsl:template match="/"> 
    <HTML> 
    <body>    
        <xsl:for-each select="employees-list/employee[salary>$MinSalary]">
            <employee>
                <name><xsl:value-of select="name"/></name>                      
                <salary><xsl:value-of select="salary"/></salary>
            </employee>
        </xsl:for-each>
    </body> 
    </HTML>     
    </xsl:template>
</xsl:stylesheet>