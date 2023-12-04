Tone : Object {

	var txt, synthdef, synth, preset;

    *new {
        arg text, synthDef;
        ^super.newCopyArgs(text, synthDef);
    }

	init {
		var textTone = this.getToneFromText(txt);
		var preset = this.getPreset(textTone);
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

	getToneFromText { | text |
		var x, textTone;

		x = "curl -X POST https://api.sapling.ai/api/v1/tone -H \"Content-Type: application/json\" \ -d \'{\"key\":\"APIKEY\", \"text\":\"" ++ text ++ "\"}\'";
		x = x.unixCmdGetStdOut;
		x.postln;
		x = x.escapeChar($");
		x = x.replace("\\", replace:"");
		x = x.parseJSON;
		textTone = x["results"][0][0][1];
		//d["results"][0][1][1].postln; // secondary tone
		//d["results"][0][2][1].postln; // tertiary tone

		^textTone;
	}

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

    getPreset { | textTone |
		preset = Dictionary.new;

		switch(textTone,
			"admiring", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"amused", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"eager", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"excited", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"grateful", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"joyful", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"loving", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"approving", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"angry", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"annoyed", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"disappointed", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"disapproving", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"repulsed", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"sad", {
				preset.put(\rate, 1);
				preset.put(\modFreq, 15);
				preset.put(\speed, 1);
				preset.put(\start, 0.1);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"mournful", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"sympathetic", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"worried", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"remorseful", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"embarassed", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"fearful", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"confused", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"relieved", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"aware", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"confident", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"curious", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"neutral", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"optimistic", {
				preset.put(\rate, 0.3);
				preset.put(\modFreq, 0.5);
				preset.put(\speed, 0.2);
				preset.put(\start, 0);
				preset.put(\end, 1);
				preset.put(\loop, 1);
			},
			"surprised", {
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
