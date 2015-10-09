package timber.log;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NonNls;

import static java.util.Collections.unmodifiableList;

/** Logging for lazy people. */
public final class Timber {
  /** Log a verbose message with optional format args. */
  public static void v(@NonNls String message, Object... args) {
    TREE_OF_SOULS.v(message, args);
  }

  /** Log a verbose exception and a message with optional format args. */
  public static void v(Throwable t, @NonNls String message, Object... args) {
    TREE_OF_SOULS.v(t, message, args);
  }

  /** Log a debug message with optional format args. */
  public static void d(@NonNls String message, Object... args) {
    TREE_OF_SOULS.d(message, args);
  }

  /** Log a debug exception and a message with optional format args. */
  public static void d(Throwable t, @NonNls String message, Object... args) {
    TREE_OF_SOULS.d(t, message, args);
  }

  /** Log an info message with optional format args. */
  public static void i(@NonNls String message, Object... args) {
    TREE_OF_SOULS.i(message, args);
  }

  /** Log an info exception and a message with optional format args. */
  public static void i(Throwable t, @NonNls String message, Object... args) {
    TREE_OF_SOULS.i(t, message, args);
  }

  /** Log a warning message with optional format args. */
  public static void w(@NonNls String message, Object... args) {
    TREE_OF_SOULS.w(message, args);
  }

  /** Log a warning exception and a message with optional format args. */
  public static void w(Throwable t, @NonNls String message, Object... args) {
    TREE_OF_SOULS.w(t, message, args);
  }

  /** Log an error message with optional format args. */
  public static void e(@NonNls String message, Object... args) {
    TREE_OF_SOULS.e(message, args);
  }

  /** Log an error exception and a message with optional format args. */
  public static void e(Throwable t, @NonNls String message, Object... args) {
    TREE_OF_SOULS.e(t, message, args);
  }

  /** Log an assert message with optional format args. */
  public static void wtf(@NonNls String message, Object... args) {
    TREE_OF_SOULS.wtf(message, args);
  }

  /** Log an assert exception and a message with optional format args. */
  public static void wtf(Throwable t, @NonNls String message, Object... args) {
    TREE_OF_SOULS.wtf(t, message, args);
  }

  /** Log at {@code priority} a message with optional format args. */
  public static void log(int priority, @NonNls String message, Object... args) {
    TREE_OF_SOULS.log(priority, message, args);
  }

  /** Log at {@code priority} an exception and a message with optional format args. */
  public static void log(int priority, Throwable t, @NonNls String message, Object... args) {
    TREE_OF_SOULS.log(priority, t, message, args);
  }

  /**
   * A view into Timber's planted trees as a tree itself. This can be used for injecting a logger
   * instance rather than using static methods or to facilitate testing.
   */
  public static Tree asTree() {
    return TREE_OF_SOULS;
  }

  /** Set a one-time tag for use on the next logging call. */
  public static Tree tag(String tag) {
    Tree[] forest = forestAsArray;
    //noinspection ForLoopReplaceableByForEach
    for (int i = 0, count = forest.length; i < count; i++) {
      forest[i].explicitTag.set(tag);
    }
    return TREE_OF_SOULS;
  }

  /** Add a new logging tree. */
  public static void plant(Tree tree) {
    if (tree == null) {
      throw new NullPointerException("tree == null");
    }
    if (tree == TREE_OF_SOULS) {
      throw new IllegalArgumentException("Cannot plant Timber into itself.");
    }
    synchronized (FOREST) {
      FOREST.add(tree);
      forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
    }
  }

  /** Remove a planted tree. */
  public static void uproot(Tree tree) {
    synchronized (FOREST) {
      if (!FOREST.remove(tree)) {
        throw new IllegalArgumentException("Cannot uproot tree which is not planted: " + tree);
      }
      forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
    }
  }

  /** Remove all planted trees. */
  public static void uprootAll() {
    synchronized (FOREST) {
      FOREST.clear();
      forestAsArray = TREE_ARRAY_EMPTY;
    }
  }

  /** Return a copy of all planted {@linkplain Tree trees}. */
  public static List<Tree> forest() {
    synchronized (FOREST) {
      return unmodifiableList(new ArrayList<>(FOREST));
    }
  }

  private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
  // Both fields guarded by 'FOREST'.
  private static final List<Tree> FOREST = new ArrayList<>();
  private static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

  /** A {@link Tree} that delegates to all planted trees in the {@linkplain #FOREST forest}. */
  private static final Tree TREE_OF_SOULS = new Tree() {
    @Override public void v(String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].v(message, args);
      }
    }

    @Override public void v(Throwable t, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].v(t, message, args);
      }
    }

    @Override public void d(String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].d(message, args);
      }
    }

    @Override public void d(Throwable t, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].d(t, message, args);
      }
    }

    @Override public void i(String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].i(message, args);
      }
    }

    @Override public void i(Throwable t, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].i(t, message, args);
      }
    }

    @Override public void w(String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].w(message, args);
      }
    }

    @Override public void w(Throwable t, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].w(t, message, args);
      }
    }

    @Override public void e(String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].e(message, args);
      }
    }

    @Override public void e(Throwable t, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].e(t, message, args);
      }
    }

    @Override public void wtf(String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].wtf(message, args);
      }
    }

    @Override public void wtf(Throwable t, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].wtf(t, message, args);
      }
    }

    @Override public void log(int priority, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].log(priority, message, args);
      }
    }

    @Override public void log(int priority, Throwable t, String message, Object... args) {
      Tree[] forest = forestAsArray;
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = forest.length; i < count; i++) {
        forest[i].log(priority, t, message, args);
      }
    }

    @Override protected void log(int priority, String tag, String message, Throwable t) {
      throw new AssertionError("Missing override for log method.");
    }
  };

  private Timber() {
    throw new AssertionError("No instances.");
  }

  /** A facade for handling logging calls. Install instances via {@link #plant Timber.plant()}. */
  public static abstract class Tree {
    private final ThreadLocal<String> explicitTag = new ThreadLocal<>();

    String getTag() {
      String tag = explicitTag.get();
      if (tag != null) {
        explicitTag.remove();
      }
      return tag;
    }

    /** Log a verbose message with optional format args. */
    public void v(String message, Object... args) {
      prepareLog(Log.VERBOSE, null, message, args);
    }

    /** Log a verbose exception and a message with optional format args. */
    public void v(Throwable t, String message, Object... args) {
      prepareLog(Log.VERBOSE, t, message, args);
    }

    /** Log a debug message with optional format args. */
    public void d(String message, Object... args) {
      prepareLog(Log.DEBUG, null, message, args);
    }

    /** Log a debug exception and a message with optional format args. */
    public void d(Throwable t, String message, Object... args) {
      prepareLog(Log.DEBUG, t, message, args);
    }

    /** Log an info message with optional format args. */
    public void i(String message, Object... args) {
      prepareLog(Log.INFO, null, message, args);
    }

    /** Log an info exception and a message with optional format args. */
    public void i(Throwable t, String message, Object... args) {
      prepareLog(Log.INFO, t, message, args);
    }

    /** Log a warning message with optional format args. */
    public void w(String message, Object... args) {
      prepareLog(Log.WARN, null, message, args);
    }

    /** Log a warning exception and a message with optional format args. */
    public void w(Throwable t, String message, Object... args) {
      prepareLog(Log.WARN, t, message, args);
    }

    /** Log an error message with optional format args. */
    public void e(String message, Object... args) {
      prepareLog(Log.ERROR, null, message, args);
    }

    /** Log an error exception and a message with optional format args. */
    public void e(Throwable t, String message, Object... args) {
      prepareLog(Log.ERROR, t, message, args);
    }

    /** Log an assert message with optional format args. */
    public void wtf(String message, Object... args) {
      prepareLog(Log.ASSERT, null, message, args);
    }

    /** Log an assert exception and a message with optional format args. */
    public void wtf(Throwable t, String message, Object... args) {
      prepareLog(Log.ASSERT, t, message, args);
    }

    /** Log at {@code priority} a message with optional format args. */
    public void log(int priority, String message, Object... args) {
      prepareLog(priority, null, message, args);
    }

    /** Log at {@code priority} an exception and a message with optional format args. */
    public void log(int priority, Throwable t, String message, Object... args) {
      prepareLog(priority, t, message, args);
    }

    /** Return whether a message at {@code priority} should be logged. */
    protected boolean isLoggable(int priority) {
      return true;
    }

    private void prepareLog(int priority, Throwable t, String message, Object... args) {
      if (!isLoggable(priority)) {
        return;
      }
      if (message != null && message.length() == 0) {
        message = null;
      }
      if (message == null) {
        if (t == null) {
          return; // Swallow message if it's null and there's no throwable.
        }
        message = Log.getStackTraceString(t);
      } else {
        if (args.length > 0) {
          message = String.format(message, args);
        }
        if (t != null) {
          message += "\n" + Log.getStackTraceString(t);
        }
      }

      log(priority, getTag(), message, t);
    }

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See {@link Log} for constants.
     * @param tag Explicit or inferred tag. May be {@code null}.
     * @param message Formatted log message. May be {@code null}, but then {@code t} will not be.
     * @param t Accompanying exceptions. May be {@code null}, but then {@code message} will not be.
     */
    protected abstract void log(int priority, String tag, String message, Throwable t);
  }

  /** A {@link Tree Tree} for debug builds. Automatically infers the tag from the calling class. */
  public static class DebugTree extends Tree {
    private static final int MAX_LOG_LENGTH = 4000;
    private static final int CALL_STACK_INDEX = 5;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

    /**
     * Extract the tag which should be used for the message from the {@code element}. By default
     * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
     * becomes {@code Foo}).
     * <p>
     * Note: This will not be called if a {@linkplain #tag(String) manual tag} was specified.
     */
    protected String createStackElementTag(StackTraceElement element) {
      String tag = element.getClassName();
      Matcher m = ANONYMOUS_CLASS.matcher(tag);
      if (m.find()) {
        tag = m.replaceAll("");
      }
      return tag.substring(tag.lastIndexOf('.') + 1);
    }

    @Override final String getTag() {
      String tag = super.getTag();
      if (tag != null) {
        return tag;
      }

      // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
      // because Robolectric runs them on the JVM but on Android the elements are different.
      StackTraceElement[] stackTrace = new Throwable().getStackTrace();
      if (stackTrace.length <= CALL_STACK_INDEX) {
        throw new IllegalStateException(
            "Synthetic stacktrace didn't have enough elements: are you using proguard?");
      }
      return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
    }

    /**
     * Break up {@code message} into maximum-length chunks (if needed) and send to either
     * {@link Log#println(int, String, String) Log.println()} or
     * {@link Log#wtf(String, String) Log.wtf()} for logging.
     *
     * {@inheritDoc}
     */
    @Override protected void log(int priority, String tag, String message, Throwable t) {
      if (message.length() < MAX_LOG_LENGTH) {
        if (priority == Log.ASSERT) {
          Log.wtf(tag, message);
        } else {
          Log.println(priority, tag, message);
        }
        return;
      }

      // Split by line, then ensure each line can fit into Log's maximum length.
      for (int i = 0, length = message.length(); i < length; i++) {
        int newline = message.indexOf('\n', i);
        newline = newline != -1 ? newline : length;
        do {
          int end = Math.min(newline, i + MAX_LOG_LENGTH);
          String part = message.substring(i, end);
          if (priority == Log.ASSERT) {
            Log.wtf(tag, part);
          } else {
            Log.println(priority, tag, part);
          }
          i = end;
        } while (i < newline);
      }
    }
  }
}
