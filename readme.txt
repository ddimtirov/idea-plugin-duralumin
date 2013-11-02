Duralumin is a simple plugin, setting custom themes for the Alloy Look
and Feel to blend better with the native desktop. It changes the fonts
and colors to the ones used by the operating system (in fact, currently
it only supports Windows).

It could be useful for people using different color themes or larger
fonts. The default theme for Alloy look nice with WinXP Luna, but it is
too bright for my desktop.

Tested with build #3193, but should work with any build that uses the
same ProjectComponent interface. If you want to play with it, the
sources and an Idea project are included in the plugin jar.

cheers,
Dimitar


KNOWN LIMITATIONS:

- The plugin applies the settings only if the Alloy LaF is set up during
  project load time. (I need to figure how the IDEA actions work before
  I fix this.)

- The colors are not finalized, what I do is to read the properties from
  AWT Toolkit and map them to an Alloy LaF theme. Since Windows and Alloy
  use different schemes, the mapping is not 1:1 and I guess it will take
  a few iterations until I get it right.

- The tool-panel buttons still use the IDEA LaF color.

- The tool-panel titles, toolbar button rollovers and progress bars are
  in IDEA style.


HISTORY:

0.2.1.
- bugfix release: 0.2 was released without the classes in the jar...

0.2.
- changed to apply the custom scheme when a project is loaded only if
  Alloy LaF is already set.

0.1
- sets Alloy LaF and applies custom scheme every time a project is
  loaded


PLANNED FEATURES:

- add configuration setting for choosing one of the standard Alloy
  themes.
- add configuration for creating custom themes, allowing the user to
  choose colors from the color picker and the system color scheme.
- add configuration for custom font themes in additional to the system
  and Alloy's default.
- Intercept the change look and feel action and apply the settings
  without having to reload the project.
- Try to resolve the remaining UI inconsistencies (probbably would have
  to walk the swing component hierarchy and tweak some component
  settings).