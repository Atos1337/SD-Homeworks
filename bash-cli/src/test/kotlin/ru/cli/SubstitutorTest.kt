package ru.cli

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SubstitutorTest {
    @Test
    fun oneVar() {
        Assertions.assertEquals(
            Token("adfab df", QuotingType.WITHOUT_QUOTE),
            Substitutor.substitute(
                Token(
                    "adfa\$a df",
                    QuotingType.WITHOUT_QUOTE
                ),
                Environment(mapOf(Pair("a", "b")) as MutableMap<String, String>)
            )
        )
    }

    @Test
    fun multipleVars() {
        Assertions.assertEquals(
            Token("adfab df^kwbf fbw wefbwvfwyubi #%63 23ncudi wt3434 t", QuotingType.DOUBLE_QUOTE),
            Substitutor.substitute(
                Token(
                    "adfa\$a df\$jdahfb134_2342___245435^kwbf fbw wefbwvfwyubi #%63 23ncudi wt3434 t",
                    QuotingType.DOUBLE_QUOTE
                ),
                Environment(mapOf(Pair("a", "b")) as MutableMap<String, String>)
            )
        )
    }

    @Test
    fun noneVars() {
        Assertions.assertEquals(
            Token("eruibhfjncd vwhuw rehfe uefvfb vgru wiedc vi fwg", QuotingType.WITHOUT_QUOTE),
            Substitutor.substitute(
                Token(
                    "eruibhfjncd vwhuw rehfe uefvfb vgru wiedc vi fwg",
                    QuotingType.WITHOUT_QUOTE
                ),
                Environment(mapOf(Pair("a", "b")) as MutableMap<String, String>)
            )
        )
    }

    @Test
    fun withSingleQuote() {
        Assertions.assertEquals(
            Token("adfa\$a df\$jdahfb134_2342___245435^kwbf fbw wefbwvfwyubi #%63 23ncudi wt3434 t", QuotingType.SINGLE_QUOTE),
            Substitutor.substitute(
                Token(
                    "adfa\$a df\$jdahfb134_2342___245435^kwbf fbw wefbwvfwyubi #%63 23ncudi wt3434 t",
                    QuotingType.SINGLE_QUOTE
                ),
                Environment(mapOf(Pair("a", "b")) as MutableMap<String, String>)
            )
        )
    }
}
