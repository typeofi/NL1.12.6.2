package javax.lang.model1;/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Source versions of the Java&trade; programming language.
 * <p>
 * See the appropriate edition of
 * <cite>The Java&trade; Language Specification</cite>
 * for information about a particular source version.
 *
 * <p>Note that additional source version constants will be added to
 * model future releases of the language.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public enum SourceVersion {
    /*
     * Summary of language evolution
     * 1.1: nested classes
     * 1.2: strictfp
     * 1.3: no changes
     * 1.4: assert
     * 1.5: annotations, generics, autoboxing, var-args...
     * 1.6: no changes
     * 1.7: diamond syntax, try-with-resources, etc.
     * 1.8: lambda expressions and default methods
     */

    /**
     * The original version.
     * <p>
     * The language described in
     * <cite>The Java&trade; Language Specification, First Edition</cite>.
     */
    RELEASE_0,

    /**
     * The version recognized by the Java Platform 1.1.
     * <p>
     * The language is {@code RELEASE_0} augmented with nested classes as described in the 1.1 update to
     * <cite>The Java&trade; Language Specification, First Edition</cite>.
     */
    RELEASE_1,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.2.
     * <p>
     * The language described in
     * <cite>The Java&trade; Language Specification,
     * Second Edition</cite>, which includes the {@code
     * strictfp} modifier.
     */
    RELEASE_2,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.3.
     * <p>
     * No major changes from {@code RELEASE_2}.
     */
    RELEASE_3,

    /**
     * The version recognized by the Java 2 Platform, Standard Edition,
     * v 1.4.
     * <p>
     * Added a simple assertion facility.
     */
    RELEASE_4,

    /**
     * The version recognized by the Java 2 Platform, Standard
     * Edition 5.0.
     * <p>
     * The language described in
     * <cite>The Java&trade; Language Specification,
     * Third Edition</cite>.  First release to support
     * generics, annotations, autoboxing, var-args, enhanced {@code
     * for} loop, and hexadecimal floating-point literals.
     */
    RELEASE_5,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 6.
     * <p>
     * No major changes from {@code RELEASE_5}.
     */
    RELEASE_6,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 7.
     * <p>
     * Additions in this release include, diamond syntax for
     * constructors, {@code try}-with-resources, strings in switch,
     * binary literals, and multi-catch.
     *
     * @since 1.7
     */
    RELEASE_7,

    /**
     * The version recognized by the Java Platform, Standard Edition
     * 8.
     * <p>
     * Additions in this release include lambda expressions and default methods.
     *
     * @since 1.8
     */
    RELEASE_8;

    // Note that when adding constants for newer releases, the
    // behavior of latest() and latestSupported() must be updated too.

    private static final SourceVersion latestSupported = getLatestSupported();
    private final static Set<String> keywords;

    static {
        Set<String> s = new HashSet<String>();
        String[] kws = {
                "abstract", "continue", "for", "new", "switch",
                "assert", "default", "if", "package", "synchronized",
                "boolean", "do", "goto", "private", "this",
                "break", "double", "implements", "protected", "throw",
                "byte", "else", "import", "public", "throws",
                "case", "enum", "instanceof", "return", "transient",
                "catch", "extends", "int", "short", "try",
                "char", "final", "interface", "static", "void",
                "class", "finally", "long", "strictfp", "volatile",
                "const", "float", "native", "super", "while",
                // literals
                "null", "true", "false"
        };
        Collections.addAll(s, kws);
        keywords = Collections.unmodifiableSet(s);
    }

    /**
     * Returns the latest source version that can be modeled.
     *
     * @return the latest source version that can be modeled
     */
    public static SourceVersion latest() {
        return RELEASE_8;
    }

    private static SourceVersion getLatestSupported() {
        try {
            String specVersion = System.getProperty("java.specification.version");

            if ("1.8".equals(specVersion))
                return RELEASE_8;
            else if ("1.7".equals(specVersion))
                return RELEASE_7;
            else if ("1.6".equals(specVersion))
                return RELEASE_6;
        } catch (SecurityException se) {
        }

//        return RELEASE_5;
        return RELEASE_8;
    }

    /**
     * Returns the latest source version fully supported by the
     * current execution environment.  {@code RELEASE_5} or later must
     * be returned.
     *
     * @return the latest source version that is fully supported
     */
    public static SourceVersion latestSupported() {
        return latestSupported;
    }

    /**
     * Returns whether or not {@code name} is a syntactically valid
     * identifier (simple name) or keyword in the latest source
     * version.  The method returns {@code true} if the name consists
     * of an initial character for which {@link
     * Character#isJavaIdentifierStart(int)} returns {@code true},
     * followed only by characters for which {@link
     * Character#isJavaIdentifierPart(int)} returns {@code true}.
     * This pattern matches regular identifiers, keywords, and the
     * literals {@code "true"}, {@code "false"}, and {@code "null"}.
     * The method returns {@code false} for all other strings.
     *
     * @param name the string to check
     * @return {@code true} if this string is a
     * syntactically valid identifier or keyword, {@code false}
     * otherwise.
     */
    public static boolean isIdentifier(CharSequence name) {
        String id = name.toString();

        if (id.isEmpty()) {
            return false;
        }
        int cp = id.codePointAt(0);
        if (!Character.isJavaIdentifierStart(cp)) {
            return false;
        }
        for (int i = Character.charCount(cp);
             i < id.length();
             i += Character.charCount(cp)) {
            cp = id.codePointAt(i);
            if (!Character.isJavaIdentifierPart(cp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not {@code name} is a syntactically valid
     * qualified name in the latest source version.  Unlike {@link
     * #isIdentifier isIdentifier}, this method returns {@code false}
     * for keywords and literals.
     *
     * @param name the string to check
     * @return {@code true} if this string is a
     * syntactically valid name, {@code false} otherwise.
     * @jls 6.2 Names and Identifiers
     */
    public static boolean isName(CharSequence name) {
        String id = name.toString();

        for (String s : id.split("\\.", -1)) {
            if (!isIdentifier(s) || isKeyword(s))
                return false;
        }
        return true;
    }

    /**
     * Returns whether or not {@code s} is a keyword or literal in the
     * latest source version.
     *
     * @param s the string to check
     * @return {@code true} if {@code s} is a keyword or literal, {@code false} otherwise.
     */
    public static boolean isKeyword(CharSequence s) {
        String keywordOrLiteral = s.toString();
        return keywords.contains(keywordOrLiteral);
    }
}
