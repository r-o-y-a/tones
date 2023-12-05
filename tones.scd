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


m = Toner.new(\samplePlayer);
var p1 = m.txt("I am very happy");
var p1 = m.txt("now i am very sad");
var p1 = m.txt("admiring");
p1.at(\pattern1).stop;










