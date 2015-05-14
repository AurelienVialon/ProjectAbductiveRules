:- modeb(*, at(+loc, +time))?
:- modeb(*, connected(+loc, +loc))?
loc(locW).
loc(locM).
loc(locE).
act(move(loc, loc)).
act(pickup).
connected(locW, locM).
connected(locM, locW).
connected(locM, locE).
connected(locE, locM).
at(locW, 0).
succeeds(pickup, 0).
at(locW, 1).
succeeds(move(locW, locM), 1).
at(locM, 2).
succeeds(move(locM, locE), 2).
at(locE, 3).
at(locW, 4).
succeeds(move(locW, locM), 4).
at(locM, 5).
succeeds(move(locM, locE), 5).
at(locE, 6).
:- modeh(succeeds(pickup, +(time)))?
:- modeh(succeeds(move(+(loc), +(loc)), +(time)))?
