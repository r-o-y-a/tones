(
b = Buffer.read(s, "/Users/roya/Documents/roya/Supercollider/samples/opz_flute.wav");
SynthDef(\modulatedSample, {|buf, speed = 1, modFreq = 2|
	var sig, lfo;
	lfo = LFSaw.kr(modFreq);
	sig = PlayBuf.ar(
		1,
		buf,
		speed * BufRateScale.kr(buf), //* lfo.linexp(-1, 1, 0.25, 4),
		loop: 1
	);
	sig = Pan2.ar(sig, lfo);
	Out.ar(0, sig);
}).add;
)


(
m = Tone.new("I am very happy", \modulatedSample);
m.init;
)
m.stopSynth();

(
h = Tone.new("so very sad", \modulatedSample);
h.init;
)
h.stopSynth();
