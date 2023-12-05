(
~samplePaths = [
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/buchla_beep.wav",
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/em.wav",
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/opz_flute.wav"
];
~buffers = ~samplePaths.collect { |path| Buffer.read(s, path) };

SynthDef(\samplePlayer, { | bufnum = 0, rate = 1, amp = 0.7, lfoFreq = 1, filterFreq = 1000, filterRes = 0.5, loop = 0, reverbMix = 0.5, pitchShift = 1, delayMaxTime = 0, delayTime = 0, decayTime = 0 |

    var lfo = SinOsc.kr(lfoFreq).range(0.8, 1.2);
    var playbackRate = rate * lfo;

	var sound = PlayBuf.ar(2, bufnum, playbackRate, loop: loop, startPos: 0, doneAction: 1);
	sound = sound *  amp;

	//sound = PitchShift.ar(sound, 2, pitchShift);
	// PitchShift_Ctor: alloc failed, increase server's RT memory (e.g. via ServerOptions)

	//delay
	sound = CombL.ar(sound, maxdelaytime: delayMaxTime, delaytime: delayTime, decaytime: decayTime);

    sound = LPF.ar(sound, filterFreq, filterRes);
	sound = FreeVerb.ar(sound, 0.5, reverbMix);

    Out.ar(0, sound);
}).add;
)


m = Toner.new(\samplePlayer);
p = m.txt("I am happy!");
p = m.txt("now i am very sad");

p.at(\pattern1).stop;
p.at(\pattern2).stop;
p.at(\pattern3).stop;



// TESTS FOR PRESETS:

(
h = Pdef(\pSample1, Pmono(
    \samplePlayer,
    \bufnum, ~buffers[2].bufnum,
	\rate, Pseq([1.0, 0.5, 1.5, 0.8], inf),
	\lfoFreq, Pseq([ Pfunc {0}], inf),
    \amp, Pseq([0.5, 0.7, 0.9, 1, 0.4, 0.2], inf),
	\filterFreq, 500,
	\filterRes, 3,
	\loop, 1,
    \reverbMix, 0.5,
	\pitchShift, 1,
	\delayMaxTime, 0,
	\delayTime, 0,
	\decayTime, 0
));
h.play;
)
h.stop;


(
q = Pdef(\pSample2, Pmono(
    \samplePlayer,
    \bufnum, ~buffers[0].bufnum,
    \rate, Pfunc { 1 },
	\lfoFreq, Pseq([ Pfunc {0}], inf),
    \amp, 0.5,
	\filterFreq, 1500,
	\filterRes, 3,
	\loop, 1,
    \reverbMix, 0.5,
	\delayMaxTime, 0,
	\delayTime, 0,
	\decayTime, 0
));
q.play;
)
q.stop;


(
y = Pdef(\pSample3, Pmono(
    \samplePlayer,
    \bufnum, ~buffers[1].bufnum,
    \rate, Pfunc { 1 },
	\lfoFreq, Pseq([ Pfunc {0}], inf),
    \amp, 0.5,
	\filterFreq, 1500,
	\filterRes, 3,
	\loop, 1,
    \reverbMix, 0.5,
	\delayMaxTime, 0,
	\delayTime, 0,
	\decayTime, 0
));
y.play;
)
y.stop;






