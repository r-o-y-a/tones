Tone : Object {

	var txt, synthdef, synth, preset;

    *new {
        arg text, synthDef;
        ^super.newCopyArgs(text, synthDef);
    }

	init {
		var preset = this.getPreset(txt);
		if (preset == nil or: { preset == "" }) {
			// do nothing
		} {

			synth = this.playSynth(
				preset[\rate],
				preset[\modFreq],
				preset[\speed],
				preset[\start],
				preset[\end],
				preset[\loop])
		}
    }

	stopSynth {
		synth.free;
	}



	/* -- private methods -- */

	playSynth { | rate, modFreq, speed, start, end, loop |

		synth = Synth(synthdef);
		synth.set(\rate, rate);
		synth.set(\modFreq, modFreq);
		synth.set(\speed, speed);
		synth.set(\start, start);
		synth.set(\end, end);
		synth.set(\loop, loop);

        ^synth;
    }

    getPreset { | text |
		preset = Dictionary.new;

		switch(text,
			"happy", {
				"happy".postln;

				preset.put(\rate, 1);
				preset.put(\modFreq, 15);
				preset.put(\speed, 1);
				preset.put(\start, 0.1);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"sad", {
				"sad".postln;

				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			{ |default|
				"neutral".postln;
			}
		);
		^preset;
	}
}
