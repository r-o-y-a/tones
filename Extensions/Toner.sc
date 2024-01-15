Toner {

	var synthdef, runOffline = false;
	var presets, patterns;

    *new {
        arg synthDef, runOffline;
		^super.newCopyArgs(synthDef, runOffline);
    }

	t { | text |
		var textTone = this.getToneFromText(text, runOffline);
		var presets = this.getPresets(textTone);

		if (presets != nil && { presets != "" }) {
			var patterns = this.playPatterns(presets, synthdef);
			^patterns;
		}
	}

	/* -- private methods -- */

	playPatterns { | presets, synthdef |
		var p1, p2, p3, p4, p5;
		var patterns = Dictionary.new;


		p1 = Pdef(\pSample1,
			Pmono(
				synthdef,
				\bufnum, ~buffers[0].bufnum,
				\rate, Pseq(~rate[0], inf),
				\lfoFreq, Pseq([ Pfunc { presets[0].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[0].at(\amp), inf),
				\filterFreq, presets[0].at(\filterFreq),
				\filterRes, presets[0].at(\filterRes),
				\loop, presets[0].at(\loop),
				\reverbMix, presets[0].at(\reverbMix),
				\pitchShift, presets[0].at(\pitchShift),
				\delayMaxTime, presets[0].at(\delayMaxTime),
				\delayTime, presets[0].at(\delayTime),
				\pitchShift, presets[0].at(\pitchShift)
		    )
		);
		patterns.put(\p1, p1);
		p1.play;


		p2 = Pdef(\pSample2,
			Pmono(
				synthdef,
				\bufnum, ~buffers[1].bufnum,
				\rate, Pseq(~rate[1], inf),
				\lfoFreq, Pseq([ Pfunc { presets[1].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[1].at(\amp), inf),
				\filterFreq, presets[1].at(\filterFreq),
				\filterRes, presets[1].at(\filterRes),
				\loop, presets[1].at(\loop),
				\reverbMix, presets[1].at(\reverbMix),
				\pitchShift, presets[1].at(\pitchShift),
				\delayMaxTime, presets[1].at(\delayMaxTime),
				\delayTime, presets[1].at(\delayTime),
				\pitchShift, presets[1].at(\pitchShift)
		    )
		);
		patterns.put(\p2, p2);
		p2.play;

		p3 = Pdef(\pSample3,
			Pmono(
				synthdef,
				\bufnum, ~buffers[2].bufnum,
				\rate, Pseq(~rate[2], inf),
				\lfoFreq, Pseq([ Pfunc { presets[2].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[2].at(\amp), inf),
				\filterFreq, presets[2].at(\filterFreq),
				\filterRes, presets[2].at(\filterRes),
				\loop, presets[2].at(\loop),
				\reverbMix, presets[2].at(\reverbMix),
				\pitchShift, presets[2].at(\pitchShift),
				\delayMaxTime, presets[2].at(\delayMaxTime),
				\delayTime, presets[2].at(\delayTime),
				\pitchShift, presets[2].at(\pitchShift)
		    )
		);
		patterns.put(\p3, p3);
		p3.play;


		p4 = Pdef(\pSample4,
			Pmono(
				synthdef,
				\bufnum, ~buffers[3].bufnum,
				\rate, Pseq(~rate[3], inf),
				\lfoFreq, Pseq([ Pfunc { presets[3].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[3].at(\amp), inf),
				\filterFreq, presets[3].at(\filterFreq),
				\filterRes, presets[3].at(\filterRes),
				\loop, presets[3].at(\loop),
				\reverbMix, presets[3].at(\reverbMix),
				\pitchShift, presets[3].at(\pitchShift),
				\delayMaxTime, presets[3].at(\delayMaxTime),
				\delayTime, presets[3].at(\delayTime),
				\pitchShift, presets[3].at(\pitchShift)
		    )
		);
		patterns.put(\p4, p4);
		p4.play;


		p5 = Pdef(\pSample5,
			Pmono(
				synthdef,
				\bufnum, ~buffers[4].bufnum,
				\rate, Pseq(~rate[4], inf),
				\lfoFreq, Pseq([ Pfunc { presets[4].at(\lfoPseqValue) }], inf),
				\amp, Pseq(presets[4].at(\amp), inf),
				\filterFreq, presets[4].at(\filterFreq),
				\filterRes, presets[4].at(\filterRes),
				\loop, presets[4].at(\loop),
				\reverbMix, presets[4].at(\reverbMix),
				\pitchShift, presets[4].at(\pitchShift),
				\delayMaxTime, presets[4].at(\delayMaxTime),
				\delayTime, presets[4].at(\delayTime),
				\pitchShift, presets[4].at(\pitchShift)
		    )
		);
		patterns.put(\p5, p5);
		p5.play;


		^patterns;
	}

	getToneFromApi { | text |
		var x, textTone;

		var path = PathName(thisProcess.nowExecutingPath).pathOnly;
		var cmd = File.readAllString(path ++ "getDataCommand.txt");

		x = (cmd + "\"" ++ text ++ "\"");
		x = x.unixCmdGetStdOut;
		textTone = x.replace("\n", replace:"");

		^textTone;
	}

	getToneFromText { | text, runOffline |
		var textTone;

		if (runOffline == false || runOffline.isNil) {
			textTone = this.getToneFromApi(text);
		};

		// run offline
		if (textTone == nil) {
			switch(text,
				"It was a cold depressing winter day.", {
					textTone = "sadness";
				},
				"But the sun was shining.", {
					textTone = "neutral";
				},
				"How nice everything looked on my way to school, I thought.", {
					textTone = "admiration";
				},
				"Suddenly, a crow flew across the horizon and startled me.", {
					textTone = "surprise";
				},
				"It went away but then came back again, flying in my face. How annoying!", {
					textTone = "annoyance";
				},
				"Go away! I said.", {
					textTone = "anger";
				},
				"It went away this time for good and everything was fine again.", {
					textTone = "relief";
				},
				"admiration", {
					textTone = "admiration";
				},
				"amusement", {
					textTone = "amusement";
				},
				"anger", {
					textTone = "anger";
				},
				"annoyance", {
					textTone = "annoyance";
				},
				"approval", {
					textTone = "approval";
				},
				"caring", {
					textTone = "caring";
				},
				"confusion", {
					textTone = "confusion";
				},
				"curiosity", {
					textTone = "curiosity";
				},
				"desire", {
					textTone = "desire";
				},
				"disappointment", {
					textTone = "disappointment";
				},
				"disapproval", {
					textTone = "disapproval";
				},
				"disgust", {
					textTone = "disgust";
				},
				"embarrassment", {
					textTone = "embarrassment";
				},
				"excitement", {
					textTone = "excitement";
				},
				"fear", {
					textTone = "fear";
				},
				"gratitude", {
					textTone = "gratitude";
				},
				"grief", {
					textTone = "grief";
				},
				"joy", {
					textTone = "joy";
				},
				"love", {
					textTone = "love";
				},
				"nervousness", {
					textTone = "nervousness";
				},
				"optimism", {
					textTone = "optimism";
				},
				"pride", {
					textTone = "pride";
				},
				"realization", {
					textTone = "realization";
				},
				"relief", {
					textTone = "relief";
				},
				"remorse", {
					textTone = "remorse";
				},
				"sadness", {
					textTone = "sadness";
				},
				"surprise", {
					textTone = "surprise";
				},
				"neutral", {
					textTone = "neutral";
				},
			);
		};

		if (textTone == nil) {
			textTone = "sad";
		};

		textTone.postln;
		^textTone;
	}

    getPresets { | textTone |
		presets = [Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new, Dictionary.new];
		switch(textTone,
			"admiration", {
				~rate = [[1, 1, 1, 1.5, 1.5, 1, 1.5, 1, 1, 1.5, 1.5, 1.8, 2, 2, 1], [0], [3], [1, 2, 1.5, 3, 1], [3]];

				presets[0].put(\amp, [0.4]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"amusement", {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"desire", {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"excitement", {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"gratitude", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"joy", {
				presets[0].put(\rate, [1, 2, 1.6, 3, 1.1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"love", {
				~rate = [[1, 2, 1.6, 3, 1.1], [0], [3], [0], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"approval", {
				~rate = [[2, 2, 2, 3, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 5], [0, 0, 0, 1.5, 1.5, 1.5, 1.5, 1.5, 1.8], [5, 6, 8], [1], [1]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"anger", {
				~rate = [[0.5, 0.2, 0.4], [0], [0.2], [1], [1]];

				presets[0].put(\amp, [0.8]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 4);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 6);

				presets[3].put(\amp, [0.2]);
				presets[3].put(\filterFreq, 3000);
				presets[3].put(\filterRes, 4);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 3000);
				presets[4].put(\filterRes, 4);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"annoyance", {
				~rate = [[1], [0], [5, 6, 8], [1], [1]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0.1);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\amp, [0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.8, 0.5, 0.4, 0.3, 0.2, 0.1]);
				presets[4].put(\filterFreq, 2000);
				presets[4].put(\filterRes, 4);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);

			},
			"disappointment", {
				~rate = [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 1, 1], [1], [5, 6, 8], [1], [1]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"disapproval", {
				~rate = [[0.5, 0.5, 0.5, 0.5, 0.5, 0.7, 0.7, 0.7, 0.7, 0.5, 0.5, 0.5], [1], [5, 6, 8], [1], [1]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 4);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 6);

				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"disgust", {
				presets[0].put(\rate, [0.5, 0.2, 0.4]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [1]);
				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 4);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [0.2]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 6);

				presets[3].put(\rate, [1]);
				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\rate, [1]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"sadness", {
				~rate = [[0.5], [6.5, 6.5, 6.5, 6.5], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.1]);
				presets[1].put(\filterFreq, 800);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0.05);
				presets[1].put(\reverbMix, 20);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 5);
				presets[1].put(\delayTime, 3);
				presets[1].put(\decayTime, 25);

				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0.1]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"grief", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"caring", {
				~rate = [[1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 1.5, 1.8, 1.8, 1.8, 1.8, 1.7, 1.7, 1.7, 1.7],[1], [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0], [1], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"nervousness", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"remorse", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"embarrassment", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [1.0, 0.5, 1.5, 0.8, 1.0, 1.0, 1.0, 1.0, 1.0]);
				presets[2].put(\amp, [0.5, 0.3, 0.4, 0.1, 0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"fear", {
				~rate = [[1.3, 1.24, 1.24, 1.24, 1.24, 1.24, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8], [0], [3], [0], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"confusion", {
				~rate = [[1, 2, 1.6, 3, 1.1], [1], [3], [0], [3]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"relief", {
				~rate = [[2.7, 2.5, 2.9, 2.6], [1], [1], [1], [1]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 3000);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 1);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0.5]);
				presets[3].put(\filterFreq, 500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0.1);
				presets[3].put(\delayTime, 0.1);
				presets[3].put(\decayTime, 4);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 1500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 1);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0);
				presets[4].put(\delayTime, 0);
				presets[4].put(\decayTime, 0);
			},
			"realization", {
				~rate = [[3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 4.5], [1.5, 2.5, 2.5, 2.5, 2.5, 3.5], [5, 6, 8], [1], [1]];


				presets[0].put(\amp, [0.3]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.3]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"pride", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"curiosity", {
				~rate = [[2.5, 2.5, 2.5, 3, 3, 2.5, 3.8, 3.8, 2.5], [2, 2, 2, 2, 2, 2, 3, 3, 3], [5, 6, 8], [1], [1]];

				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.5]);
				presets[1].put(\filterFreq, 3000);
				presets[1].put(\filterRes, 1);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 10);
				presets[1].put(\delayTime, 5);
				presets[1].put(\decayTime, 5);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"neutral", {
				~rate = [[2.5], [1], [3], [0], [3]];

				presets[0].put(\amp, [0.3]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0.1]);
				presets[1].put(\filterFreq, 1500);
				presets[1].put(\filterRes, 3);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0.1]);
				presets[3].put(\filterFreq, 1500);
				presets[3].put(\filterRes, 3);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"optimism", {
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			"surprise", {
				~rate = [[3.3, 3.7, 3.1, 3.9], [0], [2.9], [0], [3]];

				presets[0].put(\amp, [0.5, 0.4, 0.2]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0.1);
				presets[0].put(\reverbMix, 7);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\amp, [0]);
				presets[1].put(\modFreq, 0.5);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\amp, [0]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			},
			{ |default|
				presets[0].put(\rate, [1]);
				presets[0].put(\amp, [0.5]);
				presets[0].put(\filterFreq, 1500);
				presets[0].put(\filterRes, 3);
				presets[0].put(\start, 0);
				presets[0].put(\end, 1);
				presets[0].put(\loop, 1);
				presets[0].put(\lfoPseqValue, 0);
				presets[0].put(\reverbMix, 1);
				presets[0].put(\pitchShift, 0);
				presets[0].put(\delayMaxTime, 0);
				presets[0].put(\delayTime, 0);
				presets[0].put(\decayTime, 0);

				presets[1].put(\rate, [0]);
				presets[1].put(\amp, [0]);
				presets[1].put(\start, 0);
				presets[1].put(\end, 1);
				presets[1].put(\loop, 1);
				presets[1].put(\lfoPseqValue, 0);
				presets[1].put(\reverbMix, 1);
				presets[1].put(\pitchShift, 0);
				presets[1].put(\delayMaxTime, 0);
				presets[1].put(\delayTime, 0);
				presets[1].put(\decayTime, 0);

				presets[2].put(\rate, [3.0]);
				presets[2].put(\amp, [0.5]);
				presets[2].put(\filterFreq, 500);
				presets[2].put(\filterRes, 3);
				presets[2].put(\start, 0);
				presets[2].put(\end, 1);
				presets[2].put(\loop, 1);
				presets[2].put(\lfoPseqValue, 0);
				presets[2].put(\reverbMix, 4);
				presets[2].put(\pitchShift, 0);
				presets[2].put(\delayMaxTime, 0.1);
				presets[2].put(\delayTime, 0.1);
				presets[2].put(\decayTime, 4);

				presets[3].put(\rate, [0]);
				presets[3].put(\amp, [0]);
				presets[3].put(\start, 0);
				presets[3].put(\end, 1);
				presets[3].put(\loop, 1);
				presets[3].put(\lfoPseqValue, 0);
				presets[3].put(\reverbMix, 1);
				presets[3].put(\pitchShift, 0);
				presets[3].put(\delayMaxTime, 0);
				presets[3].put(\delayTime, 0);
				presets[3].put(\decayTime, 0);

				presets[4].put(\rate, [3.0]);
				presets[4].put(\amp, [0.5]);
				presets[4].put(\filterFreq, 500);
				presets[4].put(\filterRes, 3);
				presets[4].put(\start, 0);
				presets[4].put(\end, 1);
				presets[4].put(\loop, 1);
				presets[4].put(\lfoPseqValue, 0);
				presets[4].put(\reverbMix, 4);
				presets[4].put(\pitchShift, 0);
				presets[4].put(\delayMaxTime, 0.1);
				presets[4].put(\delayTime, 0.1);
				presets[4].put(\decayTime, 4);
			}
		);
		^presets;
	}
}
