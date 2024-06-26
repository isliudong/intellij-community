// "Wrap with '?.let { ... }' call" "true"
// WITH_STDLIB
// ERROR: Operator call corresponds to a dot-qualified call 'arg?.let { 24.hashCode().foo(it) }.plus(1)' which is not allowed on a nullable receiver 'arg?.let { 24.hashCode().foo(it) }'.

fun Int.foo(x: Int) = this + x

val arg: Int? = 42

val res: Int = 24.hashCode().foo(<caret>arg) + 1
// FUS_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.WrapWithSafeLetCallFix
// FUS_K2_QUICKFIX_NAME: org.jetbrains.kotlin.idea.k2.codeinsight.fixes.WrapWithSafeLetCallFixFactories$WrapWithSafeLetCallModCommandAction