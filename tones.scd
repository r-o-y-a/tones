(
s.waitForBoot {

a = PathName(thisProcess.nowExecutingPath).pathOnly ++ "samples/";
~buffers = PathName(a).entries.collect{|path| Buffer.read(s, path.fullPath)};
//a[0].play;

SynthDef(\samplePlayer, { | bufnum = 0, rate = 1, amp = 0.7, lfoFreq = 1, filterFreq = 1000, filterRes = 0.5, loop = 0, reverbMix = 0.5, pitchShift = 1, delayMaxTime = 0, delayTime = 0, decayTime = 0, gate = 1 |

    var lfo = SinOsc.kr(lfoFreq).range(0.8, 1.2);
    var playbackRate = rate * lfo;
	var sound = PlayBuf.ar(2, bufnum, playbackRate, loop: gate, startPos: 0);
	sound = sound *  amp;

	//sound = PitchShift.ar(sound, 2, pitchShift);
	// PitchShift_Ctor: alloc failed, increase server's RT memory (e.g. via ServerOptions)

	//sound = Warp1.ar(1, sound, MouseX.kr(0,1), MouseY.kr(0.5,2), 0.1, -1, 8, 0.1, 2);

	// delay
	sound = CombL.ar(sound, maxdelaytime: delayMaxTime, delaytime: delayTime, decaytime: decayTime);

	sound = LPF.ar(sound, filterFreq, filterRes);
    //sound = HPF.ar(sound, filterFreq, filterRes); // test
	sound = FreeVerb.ar(sound, 0.5, reverbMix) *0.1;
	DetectSilence.ar(sound, doneAction: 2);

    Out.ar(0, sound);
}).add;

s.sync;

t = Toner.new(\samplePlayer);
}
);


p = t.t("It was a cold winter day.");
p = t.t("But the sun was shining.");
p = t.t("How nice everything looked on my way to school, I thought.");
p = t.t("Suddenly, a crow flew across the horizon and startled me.");
p = t.t("It went away but then came back again, flying in my face.");
p = t.t("Go away! I said.");
p = t.t("neutral");
p = t.t("It was a cold winter day.");



p.at(\p1).stop;
p.at(\p2).stop;
p.at(\p3).stop;
p.at(\p4).stop;
p.at(\p5).stop;






















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






