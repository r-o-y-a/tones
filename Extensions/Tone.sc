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

		"getPreset: " + textTone.postln;

		switch(textTone,
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
