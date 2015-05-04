:- modeh(1, aunt_of(+person, +person))?
:- modeh(*, sister_of(+person, -person))?
:- modeb(*, parent_of(-person, +person))?
:- modeb(*, parent_of(+person, -person))?
:- modeb(*, sister_of(+person, -person))?
:- modeb(*, male(+person))?
:- modeb(*, female(+person))?
person(martin).
person(jean).
person(jacques).
person(henry).
person(jim).
person(sam).
person(brice).
person(kevin).
person(lorie).
person(sarah).
person(judy).
person(sally).
person(jeanne).
person(charline).
person(laura).
person(emilie).
person(cendrillon).
person(jasmine).
person(mulan).
person(arielle).
male(martin).
male(jean).
male(jacques).
male(henry).
male(jim).
male(sam).
male(brice).
male(kevin).
female(lorie).
female(sarah).
female(judy).
female(sally).
female(jeanne).
female(charline).
female(laura).
female(emilie).
female(cendrillon).
female(jasmine).
female(esmeralda).
female(mulan).
female(arielle).
parent_of(Parent, Child) :- father_of(Parent, Child).
parent_of(Parent, Child) :- mother_of(Parent, Child).
father_of(martin, brice).
father_of(martin, laura).
father_of(martin, kevin).
mother_of(emilie, judy).
mother_of(emilie, sarah).
father_of(jacques, sam).
father_of(jacques, jeanne).
father_of(sam, henry).
father_of(sam, sally).
mother_of(sarah, jim).
mother_of(charline, sally).
mother_of(charline, lorie).
aunt_of(jeanne, henry).
aunt_of(judy, jim).
sister_of(jeanne, sam).
sister_of(judy, sarah).
sister_of(sarah, judy).
sister_of(laura, brice).
:- sister_of(brice, laura).
sister_of(laura, kevin).
:- set(posonly)?
:- set(r, 1000)?
:- set(h, 1000)?
