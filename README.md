Tones uses sentiment analysis for text classification and returns music based on the tone.
It is meant to be performed in a live-coding situation.


How to Run (OSX):

No API necessary, this will run locally based on hard-coded text.

- Install SuperCollider
- Copy Extensions/Toner.sc to ~/Library/Application Support/SuperCollider/Extensions
- In tones.scd, make sure to run it offline: t = Toner.new(\samplePlayer, true);
- Run the first block (buffer/synthdef)
- Run a few hard-coded test phrases (non-hardcoded phrases will default to "sadness"):
- p = t.t("It was a cold depressing winter day."); // sadness
- p = t.t("But the sun was shining."); // neutral
- p = t.t("Go away! I said."); // anger
- 5 synths should be loaded with each text, these can be steered individually.