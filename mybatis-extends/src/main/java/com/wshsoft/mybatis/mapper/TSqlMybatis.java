package com.wshsoft.mybatis.mapper;

import com.wshsoft.mybatis.MybatisAbstractSQL;
import com.wshsoft.mybatis.toolkit.CollectionUtil;
import com.wshsoft.mybatis.toolkit.StringUtils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 实现AbstractSQL ，实现WHERE条件自定义
 * </p>
 *
 * @author carry xie
 * @Date 2016-08-22
 */
@SuppressWarnings("serial")
public class TSqlMybatis extends MybatisAbstractSQL<TSqlMybatis> {

    private final String IS_NOT_NULL = " IS NOT NULL";
    private final String IS_NULL = " IS NULL";
    private final String SQL_LIKE = " LIKE CONCAT(CONCAT({0},{1}),{2})";
    private final String SQL_BETWEEN_AND = " BETWEEN {0} AND {1}";

    @Override
    public TSqlMybatis getSelf() {
        return this;
    }

    /**
     * 将LIKE语句添加到WHERE条件中
     *
     * @param column
     *            字段名
     * @param value
     *            like值,无需前后%, MYSQL及ORACEL通用
     * @return
     */
    public TSqlMybatis LIKE(String column, String value) {
        handerLike(column, value, false);
        return this;
    }

    /**
     * 将LIKE语句添加到WHERE条件中
     *
     * @param column
     *            字段名
     * @param value
     *            like值,无需前后%, MYSQL及ORACEL通用
     * @return
     */
    public TSqlMybatis NOT_LIKE(String column, String value) {
        handerLike(column, value, true);
        return this;
    }

    /**
     * IS NOT NULL查询
     *
     * @param columns
     *            以逗号分隔的字段名称
     * @return this
     */
    public TSqlMybatis IS_NOT_NULL(String columns) {
        handerNull(columns, IS_NOT_NULL);
        return this;
    }

    /**
     * IS NULL查询
     *
     * @param columns
     *            以逗号分隔的字段名称
     * @return
     */
    public TSqlMybatis IS_NULL(String columns) {
        handerNull(columns, IS_NULL);
        return this;
    }

    /**
     * 处理LIKE操作
     *
     * @param column
     *            字段名称
     * @param value
     *            like匹配值
     * @param isNot
     *            是否为NOT LIKE操作
     */
    private void handerLike(String column, String value, boolean isNot) {
        if (StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(value)) {
            StringBuilder inSql = new StringBuilder();
            inSql.append(column);
            if (isNot) {
                inSql.append(" NOT");
            }
            inSql.append(MessageFormat.format(SQL_LIKE, "'%'", StringUtils.quotaMark(value), "'%'"));
            WHERE(inSql.toString());
        }
    }

    /**
     * 将IN语句添加到WHERE条件中
     *
     * @param column
     *            字段名
     * @param value
     *            List集合
     * @return
     */
    public TSqlMybatis IN(String column, Collection<?> value) {
        handerIn(column, value, false);
        return this;
    }

    /**
     * 将IN语句添加到WHERE条件中
     *
     * @param column
     *            字段名
     * @param value
     *            List集合
     * @return
     */
    public TSqlMybatis NOT_IN(String column, Collection<?> value) {
        handerIn(column, value, true);
        return this;
    }

    /**
     * 将IN语句添加到WHERE条件中
     *
     * @param column
     *            字段名
     * @param value
     *            逗号拼接的字符串
     * @return
     */
    public TSqlMybatis IN(String column, String value) {
        handerIn(column, value, false);
        return this;
    }

    /**
     * 将IN语句添加到WHERE条件中
     *
     * @param column
     *            字段名
     * @param value
     *            逗号拼接的字符串
     * @return
     */
    public TSqlMybatis NOT_IN(String column, String value) {
        handerIn(column, value, true);
        return this;
    }

    /**
     * 将EXISTS语句添加到WHERE条件中
     *
     * @param value
     * @return
     */
    public TSqlMybatis EXISTS(String value) {
        handerExists(value, false);
        return this;
    }

    /**
     * 处理EXISTS操作
     *
     * @param value
     * @param isNot
     *            是否为NOT EXISTS操作
     */
    private void handerExists(String value, boolean isNot) {
        if (StringUtils.isNotEmpty(value)) {
            StringBuilder inSql = new StringBuilder();
            if (isNot) {
                inSql.append(" NOT");
            }
            inSql.append(" EXISTS (").append(value).append(")");
            WHERE(inSql.toString());
        }
    }

    /**
     * 将NOT_EXISTS语句添加到WHERE条件中
     *
     * @param value
     * @return
     */
    public TSqlMybatis NOT_EXISTS(String value) {
        handerExists(value, true);
        return this;
    }

    /**
     * 处理IN操作
     *
     * @param column
     *            字段名称
     * @param value
     *            集合List
     * @param isNot
     *            是否为NOT IN操作
     */
    private void handerIn(String column, Collection<?> value, boolean isNot) {
        if (StringUtils.isNotEmpty(column) && CollectionUtil.isNotEmpty(value)) {
            StringBuilder inSql = new StringBuilder();
            inSql.append(column);
            if (isNot) {
                inSql.append(" NOT");
            }
            inSql.append(" IN (");
            int _size = value.size();
            int i = 0;
            Iterator<?> iterator = value.iterator();
            while(iterator.hasNext()) {
                String tempVal = StringUtils.quotaMark(iterator.next());
                if (i + 1 == _size) {
                    inSql.append(tempVal);
                } else {
                    inSql.append(tempVal);
                    inSql.append(",");
                }
                i++;
            }
            inSql.append(")");
            WHERE(inSql.toString());
        }
    }

    /**
     * 处理IN操作
     *
     * @param column
     *            字段名称
     * @param value
     *            逗号拼接的字符串
     * @param isNot
     *            是否为NOT IN操作
     */
    private void handerIn(String column, String value, boolean isNot) {
        if (StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(value)) {
            StringBuilder inSql = new StringBuilder();
            inSql.append(column);
            if (isNot) {
                inSql.append(" NOT");
            }
            inSql.append(" IN (").append(value).append(")");
            WHERE(inSql.toString());
        }
    }

    /**
     * 处理BETWEEN_AND操作
     *
     * @param column
     *            字段名称
     * @param val1
     * @param val2
     */
    public TSqlMybatis BETWEEN_AND(String column, String val1, String val2) {
        between(column, val1, val2);
        return this;
    }

    /**
     * 处理BETWEEN_AND操作
     *
     * @param column
     *            字段名称
     * @param val1
     * @param val2
     */
    private void between(String column, String val1, String val2) {
        if (StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(val1) && StringUtils.isNotEmpty(val2)) {
            StringBuilder betweenSql = new StringBuilder();
            betweenSql.append(column);
            betweenSql.append(MessageFormat.format(SQL_BETWEEN_AND, StringUtils.quotaMark(val1),StringUtils.quotaMark(val2)));
            WHERE(betweenSql.toString());
        }
    }

    /**
     * 以相同的方式处理null和notnull
     *
     * @param columns
     *            以逗号分隔的字段名称
     * @param sqlPart
     *            SQL部分
     */
    private void handerNull(String columns, String sqlPart) {
        if (StringUtils.isNotEmpty(columns)) {
            String[] cols = columns.split(",");
            for (String col : cols) {
                if (StringUtils.isNotEmpty(col.trim())) {
                    WHERE(col + sqlPart);
                }
            }
        }
    }
}
