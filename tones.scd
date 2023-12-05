//play({ Pan2.ar(PinkNoise.ar(0.1), FSinOsc.kr(1)); });

(
~samplePaths = [
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/buchla_beep.wav",
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/em.wav",
    "/Users/roya/Documents/roya/Supercollider/KMH/tones/samples/opz_flute.wav"
];
~buffers = ~samplePaths.collect { |path| Buffer.read(s, path) };

SynthDef(\samplePlayer, { | bufnum = 0, rate = 1, amp = 0.7, lfoFreq = 1, envAttack = 0.1, envRelease = 1, filterFreq = 1000, filterRes = 0.5, loop = 0 |

    var lfo = SinOsc.kr(lfoFreq).range(0.8, 1.2);
    var playbackRate = rate * lfo;

	var sound = PlayBuf.ar(2, bufnum, playbackRate, loop: loop, startPos: 0, doneAction: 1);
	sound = sound *  amp;

    sound = LPF.ar(sound, filterFreq, filterRes);

    Out.ar(0, sound);
}).add;
)



]

(
~rate1 = 1;
~pseqvalue1 = 0;
p = Pdef(\pSample1, Pmono(
    \samplePlayer,
    \bufnum, ~buffers[2].bufnum,
    \rate, Pfunc { ~rate1 },
	\lfoFreq, Pseq([ Pfunc {~pseqvalue1}], inf),
    \amp, 0.5,
	\envAttack, 0.2,
	\envRelease, 10,
	\filterFreq, 1500,
	\filterRes, 3,
	\loop, 1,
    \speed, Pseq([1, 0.8, 1.2], inf),
    \dur, 1,
    \gate, 1
));
p.play;
)
p.stop;

~rate1 = 1.05;
~pseqvalue1 = 20;




(
~rate2 = 1;
~pseqvalue2 = 0;
q = Pdef(\pSample2, Pmono(
    \samplePlayer,
    \bufnum, ~buffers[2].bufnum,
    \rate, Pfunc { ~rate2 },
	\lfoFreq, Pseq([ Pfunc {~pseqvalue2}], inf),
    \amp, 0.5,
	\envAttack, 0.2,
	\envRelease, 10,
	\filterFreq, 1500,
	\filterRes, 3,
	\loop, 1,
    \speed, Pseq([1, 0.8, 1.2], inf),
    \dur, 1,
    \gate, 1
));
q.play;
)
q.stop;

~rate2 = 1;
~pseqvalue2 = 50;







