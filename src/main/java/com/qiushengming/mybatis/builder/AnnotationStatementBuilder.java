package com.qiushengming.mybatis.builder;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;

import java.util.Locale;

/**
 * @author MinMin
 */
public class AnnotationStatementBuilder
    extends BaseBuilder {

    private MapperBuilderAssistant builderAssistant;
    private Statement statement;
    private String requiredDatabaseId;

    public AnnotationStatementBuilder(Configuration configuration,
        MapperBuilderAssistant builderAssistant, Statement statement) {
        this(configuration, builderAssistant, statement, null);
    }

    AnnotationStatementBuilder(Configuration configuration,
        MapperBuilderAssistant builderAssistant, Statement statement,
        String databaseId) {
        super(configuration);
        this.builderAssistant = builderAssistant;
        this.statement = statement;
        this.requiredDatabaseId = databaseId;
    }

    void parseStatementNode() {
        String id = statement.getId();
        String databaseId = statement.getDatabaseId();

        if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
            return;
        }

        String parameterType = statement.getParameterType();
        Class<?> parameterTypeClass = resolveClass(parameterType);
        String resultMap = statement.getResultMap();
        String resultType = statement.getResultType();
        String lang = statement.getLang();
        LanguageDriver langDriver = getLanguageDriver(lang);

        Class<?> resultTypeClass = resolveClass(resultType);
        String resultSetType = statement.getResultSetType();
        StatementType statementType =
            StatementType.valueOf(StatementType.PREPARED.toString());
        ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);

        SqlCommandType sqlCommandType =
            SqlCommandType.valueOf(statement.getSqlCommandType()
                .toUpperCase(Locale.ENGLISH));
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean useCache = isSelect;
        boolean resultOrdered = false;

        SqlSource sqlSource =
            new DynamicSqlSource(configuration, new TextSqlNode("${_sql}"));

        builderAssistant.addMappedStatement(id,
            sqlSource,
            statementType,
            sqlCommandType,
            null,
            null,
            null,
            parameterTypeClass,
            resultMap,
            resultTypeClass,
            resultSetTypeEnum,
            flushCache,
            useCache,
            resultOrdered,
            null,
            null,
            null,
            databaseId,
            langDriver,
            null);
    }

    private boolean databaseIdMatchesCurrent(String id, String databaseId,
        String requiredDatabaseId) {
        if (requiredDatabaseId != null) {
            if (!requiredDatabaseId.equals(databaseId)) {
                return false;
            }
        } else {
            if (databaseId != null) {
                return false;
            }
            id = builderAssistant.applyCurrentNamespace(id, false);
            if (this.configuration.hasStatement(id, false)) {
                // issue
                MappedStatement previous =
                    this.configuration.getMappedStatement(id, false);
                if (previous.getDatabaseId() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private LanguageDriver getLanguageDriver(String lang) {
        Class<?> langClass = null;
        if (lang != null) {
            langClass = resolveClass(lang);
        }
        return builderAssistant.getLanguageDriver(langClass);
    }

}
