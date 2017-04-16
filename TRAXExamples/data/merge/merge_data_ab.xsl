<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Orig. http://stackoverflow.com/questions/1510688/xslt-a-simple-way-to-merge-xml-files -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
  <xsl:variable name="with" select="'data_b.xml'" />
  <xsl:variable name='newline'><xsl:text>
</xsl:text></xsl:variable>

  <xsl:template match="/">
    <xsl:copy>
      <xsl:apply-templates select="employees-list"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="employees-list">
	<xsl:text>&#xA;</xsl:text>
	<xsl:comment>Data From data_a.xml</xsl:comment>
	<xsl:text>&#xA;</xsl:text>
    <xsl:copy-of select="./employee"/>
    <xsl:variable name="info" select="document($with)/employees-list/employee"/>
	<xsl:value-of select="$newline"/>
	<xsl:comment>Data From data_b.xml</xsl:comment>
	<xsl:value-of select="$newline"/>
	 <xsl:copy-of select="$info"/>
  </xsl:template>
</xsl:stylesheet>
