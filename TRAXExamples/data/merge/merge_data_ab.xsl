<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Orig. http://stackoverflow.com/questions/1510688/xslt-a-simple-way-to-merge-xml-files -->
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
  <xsl:variable name="with" select="'./data/merge/data_b.xml'" />

  <xsl:template match="/">
    <xsl:copy>
      <xsl:apply-templates select="employees-list" />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="employees-list">
    <xsl:copy select="./employee"/>
       <xsl:copy>
      <xsl:variable name="info" select="document($with)/employees-list/employee" />
      <xsl:for-each select="$info/*">
          <xsl:copy-of select="." />
      </xsl:for-each>
    </xsl:copy>
  </xsl:template>
</xsl:transform>
