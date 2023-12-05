Toner : Object {

	var synthdef, presets, patterns;

    *new {
        arg synthDef;
		^super.new.init(synthDef);
    }

	init { | synthDef |
		synthdef = synthDef;
    }

	txt { | text |
		var textTone = this.getToneFromText(text);
		var presets = this.getPresets(textTone);
		if (presets == nil or: { presets == "" }) {
			// do nothing
		} {
			var patterns = this.playPatterns(presets, synthdef);
			^patterns;
		}
	}

	/* -- private methods -- */

	playPatterns { | presets, synthdef |
		var p1, p2, p3;
		var patterns = Dictionary.new;

		p1 = Pdef(\pSample1,
			Pmono(
				synthdef,
				\bufnum, ~buffers[2].bufnum,
				\rate, Pfunc { presets[0].at(\rate) },
				\lfoFreq, Pseq([ Pfunc { presets[0].at(\lfoPseqValue) }], inf),
				\amp, 0.5,
				\envAttack, 0.2,
				\envRelease, 10,
				\filterFreq, 1500,
				\filterRes, 3,
				\loop, 1,
				\speed, Pseq([1, 0.8, 1.2], inf),
				\dur, 1,
				\gate, 1
		    )
		);
		patterns.put(\pattern1, p1);
		p1.play;

		p2 = Pdef(\pSample2,
			Pmono(
				synthdef,
				\bufnum, ~buffers[1].bufnum,
				\rate, Pfunc { presets[1].at(\rate) },
				\lfoFreq, Pseq([ Pfunc { presets[1].at(\lfoPseqValue) }], inf),
				\amp, 0.5,
				\envAttack, 0.2,
				\envRelease, 10,
				\filterFreq, 1500,
				\filterRes, 3,
				\loop, 1,
				\speed, Pseq([1, 0.8, 1.2], inf),
				\dur, 1,
				\gate, 1
		    )
		);
		patterns.put(\pattern2, p2);
		p2.play;


		p3 = Pdef(\pSample3,
			Pmono(
				synthdef,
				\bufnum, ~buffers[0].bufnum,
				\rate, Pfunc { presets[2].at(\rate) },
				\lfoFreq, Pseq([ Pfunc { presets[2].at(\lfoPseqValue) }], inf),
				\amp, 0.5,
				\envAttack, 0.2,
				\envRelease, 10,
				\filterFreq, 1500,
				\filterRes, 3,
				\loop, 1,
				\speed, Pseq([1, 0.8, 1.2], inf),
				\dur, 1,
				\gate, 1
		    )
		);
		patterns.put(\pattern3, p3);
		p3.play;


		^patterns;
	}

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

    getPresets { | textTone |
		presets = [Dictionary.new, Dictionary.new, Dictionary.new];

		switch(textTone,
			"admiring", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"amused", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"eager", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"excited", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"grateful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"joyful", {
				presets[0].put(\rate, 1);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"loving", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"approving", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"angry", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"annoyed", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"disappointed", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"disapproving", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"repulsed", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"sad", {
				presets[0].put(\rate, 5);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"mournful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"sympathetic", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"worried", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"remorseful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"embarassed", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"fearful", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"confused", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"relieved", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"aware", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"confident", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"curious", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"neutral", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"optimistic", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			"surprised", {
				presets[0].put(\rate, 0.3);
				presets[0].put(\modFreq, 0.5);
				presets[0].put(\speed, 0.2);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);

				presets[1].put(\rate, 0.3);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\speed, 0.2);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);

				presets[2].put(\rate, 0.3);
				presets[2].put(\modFreq, 0.5);
				presets[2].put(\speed, 0.2);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
			},
			{ |default|
				"neutral".postln;
			}
		);
		^presets;
	}
}
