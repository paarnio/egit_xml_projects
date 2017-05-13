<xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
            xmlns:siima="http://siima.net/test/">
<!-- filtering_asp_models.xsl test-version-->
<!-- -->			
<!-- method xml-->
<xsl:output method="xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<xsl:template match="/">  
            <xsl:apply-templates select="aspmodels"/>
</xsl:template>

<xsl:template match="aspmodels">
    <xsl:apply-templates select="./model" mode="mainbranch" />
	<xsl:apply-templates select="./model" mode="subbranch" />
	<!--xsl:apply-templates select="./model" mode="createlego" /-->
	<xsl:for-each select="./model">
	<xsl:text>&#xA;MOD NUMBER</xsl:text>
	<xsl:value-of select="./@num"/>
	<xsl:call-template name="createlego">
        <xsl:with-param name="BranchType" select="'mainbranch'"/>
		<xsl:with-param name="model" select="."/>
    </xsl:call-template>
	<xsl:call-template name="createlego">
        <xsl:with-param name="BranchType" select="'subbranch'"/>
		<xsl:with-param name="model" select="."/>
    </xsl:call-template>
    </xsl:for-each>
	
</xsl:template>

<xsl:template match="model" mode="koe1">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="./literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<xsl:variable name="Level" select="./arg[@num=1]"/>
		<xsl:variable name="MBRBool" select="boolean(//literal[(@predicate='mainbranch')]/arg[@num=0]=string($LEGO))"/>
		<xsl:value-of select="$Level"/>
		<xsl:text>:</xsl:text>		
		<xsl:value-of select="$LEGO"/>
		<xsl:text>:</xsl:text>		
		<xsl:value-of select="$MBRBool"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:for-each>
</xsl:template>

<xsl:template match="model" mode="mainbranch">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="./literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<xsl:variable name="Level" select="./arg[@num=1]"/>
		<xsl:variable name="MBRBool" select="boolean(//literal[(@predicate='mainbranch')]/arg[@num=0]=string($LEGO))"/>
		<xsl:choose>			
			<xsl:when test="$MBRBool">
			<xsl:text>&#xA;</xsl:text>
			<xsl:text>MAIN-BRANCH:</xsl:text>			
			<xsl:value-of select="$Level"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$LEGO"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$MBRBool"/>
		</xsl:when>
		</xsl:choose>
		
	</xsl:for-each>
</xsl:template>

<xsl:template match="model" mode="subbranch">
	<xsl:variable name="Modnum" select="./@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="./literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<xsl:variable name="Level" select="./arg[@num=1]"/>
		<xsl:variable name="MBRBool" select="boolean(//literal[(@predicate='mainbranch')]/arg[@num=0]=string($LEGO))"/>
		<xsl:choose>			
			<xsl:when test="not($MBRBool)">
			<xsl:text>&#xA;</xsl:text>
			<xsl:text>SUB-BRANCH:</xsl:text>			
			<xsl:value-of select="$Level"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$LEGO"/>
			<xsl:text>:</xsl:text>		
			<xsl:value-of select="$MBRBool"/>
		</xsl:when>
		</xsl:choose>		
	</xsl:for-each>
</xsl:template>


<xsl:template name="createlego">
<!--xsl:template name=match="model" mode="createlego"-->
	<xsl:param name="BranchType"/>
	<xsl:param name="model"/>
	<xsl:text>&#xA;MODELLL</xsl:text>
	<xsl:value-of select="$model/@num"/>
	<xsl:value-of select="$BranchType"/>
	<xsl:variable name="Modnum" select="$model/@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="$model/literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<!--xsl:variable name="Level" select="./arg[@num=1]"/-->
		<xsl:variable name="BRBool" select="boolean(//literal[(@predicate==string($BranchType))]/arg[@num=0]=string($LEGO))"/>
		<!-- OK -->
		<!--xsl:variable name="LegType" select="boolean(//literal[(@predicate=string($legType))]/arg[@num=0]=string($LEGO))"/-->
		
		<xsl:variable name="RecBool" select="boolean(//literal[(@predicate='rectangle')]/arg[@num=0]=string($LEGO))"/>
		<xsl:variable name="SquBool" select="boolean(//literal[(@predicate='square')]/arg[@num=0]=string($LEGO))"/>
		<xsl:choose>			
			<xsl:when test="$BRBool">
			<xsl:text>&#xA;</xsl:text>
			<!--xsl:text>BRANCH create: legos</xsl:text-->
				<xsl:choose>			
				<xsl:when test="$RecBool">
					<xsl:variable name="legType" select="'rectangle'"/>
				</xsl:when>
				<xsl:when test="$SquBool">
					<xsl:variable name="legType" select="'square'"/>
				</xsl:when>
				</xsl:choose>
			<xsl:variable name="CreBlego" select="concat('create ',string($legType),' ',string($LEGO))"/>
			<xsl:value-of select="$CreBlego"/>
		</xsl:when>
		</xsl:choose>		
	</xsl:for-each>
</xsl:template>

<xsl:template name="createlegoByType">
<!--xsl:template name=match="model" mode="createlego"-->
	<xsl:param name="legType"/>
	<xsl:param name="model"/>
	<xsl:text>&#xA;MODELLL</xsl:text>
	<xsl:value-of select="$model/@num"/>
	<xsl:value-of select="$legType"/>
	<xsl:variable name="Modnum" select="$model/@num"/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="$model/literal[(@predicate='level')]">
		<xsl:sort select="./arg[@num=1]"/>
		<xsl:variable name="LEGO" select="./arg[@num=0]"/>
		<xsl:variable name="Level" select="./arg[@num=1]"/>
		<xsl:variable name="MBRBool" select="boolean(//literal[(@predicate='mainbranch')]/arg[@num=0]=string($LEGO))"/>
		<!-- OK -->
		<xsl:variable name="TypBool" select="boolean(//literal[(@predicate=string($legType))]/arg[@num=0]=string($LEGO))"/>
		
		<xsl:variable name="RecBool" select="boolean(//literal[(@predicate='rectangle')]/arg[@num=0]=string($LEGO))"/>
		<xsl:variable name="SquBool" select="boolean(//literal[(@predicate='square')]/arg[@num=0]=string($LEGO))"/>
		<xsl:choose>			
			<xsl:when test="($MBRBool) and ($TypBool)">
			<xsl:text>&#xA;</xsl:text>
			<!--xsl:text>MAIN-BRANCH REC:</xsl:text-->
			<xsl:variable name="CreMain" select="concat('create ',string($legType),' ',string($LEGO))"/>
			<xsl:value-of select="$CreMain"/>
		</xsl:when>
		</xsl:choose>		
	</xsl:for-each>
</xsl:template>


</xsl:stylesheet>