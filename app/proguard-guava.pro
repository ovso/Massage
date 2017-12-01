-injars path/to/myapplication.jar
-injars lib/guava-r07.jar
-libraryjars lib/jsr305.jar
-outjars myapplication-dist.jar

-dontoptimize
-dontobfuscate
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}