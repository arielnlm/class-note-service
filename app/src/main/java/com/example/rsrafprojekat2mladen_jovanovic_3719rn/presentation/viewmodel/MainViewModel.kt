package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Class
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.NoteEntity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Resource
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories.ClassRepository
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories.NoteRepository
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.contract.MainContract
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states.ClassesState
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states.NotesState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.io.Console
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class MainViewModel(
    private val classRepository: ClassRepository,
    private val noteRepository: NoteRepository,
): ViewModel(), MainContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val classState: MutableLiveData<ClassesState> = MutableLiveData()
    override val notesState: MutableLiveData<NotesState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    private val publishSubject2: PublishSubject<String> = PublishSubject.create()

    init {

       /* val sub = publishSubject2
            .switchMap {
                noteRepository
                    .getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }.subscribe(
                {
                    notesState.value = NotesState.Success(it)
                },
                {
                    notesState.value = NotesState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )*/
        Timber.e("Poziv inita")
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                Timber.e("Poziv mape " + it.split(",").size)
                if(it.split(",").size == 3) {
                    classRepository
                        .getByDayClassName(it.split(",")[0], it.split(",")[1], it.split(",")[2])
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError {
                            Timber.e("Error in publish subject")
                            Timber.e(it)
                        }
                }
                else{
                    noteRepository
                        .getAllByTitleOrContent(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError {
                            Timber.e("Error in publish subject")
                            Timber.e(it)
                        }
                }
            }
            .subscribe(
                {
                    if(it[0]::class == Class::class){
                        classState.value = ClassesState.Success(it as List<Class>)
                    }
                    else{
                        notesState.value = NotesState.Success(it as List<Note>)
                    }
                    Timber.e("Succes fetch jes")
                },
                {
                    classState.value = ClassesState.Error("Error happend while initlay fetching data from db")
                    notesState.value = NotesState.Error("error notes")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)

        insertNote(Note(1, "notes1", "tekst", false, "2222-06-07"))
        insertNote(Note(2, "notes2", "tekst", false, "2222-06-06"))

        insertNote(Note(3, "notes3", "tekst", false, "2222-06-07"))
        insertNote(Note(4, "notes4", "tekst", false, "2222-06-05"))

        insertNote(Note(5, "notes5", "tekst", false, "2222-06-04"))

        insertNote(Note(6, "notes6", "tekst", false, "2222-06-05"))

        insertNote(Note(7, "notes7", "tekst", false, "2222-06-07"))


    }

    override fun fetchAllClasses() {
        val subscription = classRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> classState.value = ClassesState.Loading
                        is Resource.Success -> classState.value = ClassesState.DataFetched
                        is Resource.Error -> classState.value = ClassesState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    classState.value = ClassesState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllClasses() {
        val subscription = classRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    classState.value = ClassesState.Success(it)
                },
                {
                    classState.value = ClassesState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getClassesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun getClassesByNameDayClass(name: String, day: String, className: String) {
        publishSubject.onNext(name+","+day+","+className)
    }

    override fun getAllNotes() {
        val subscription = noteRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    notesState.value = NotesState.Success(it)
                },
                {
                    notesState.value = NotesState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
        //publishSubject2.onNext("test")
    }

    override fun getAllNotes2() {
        val subscription = noteRepository
            .getAll2()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    notesState.value = NotesState.Success(it)
                },
                {
                    notesState.value = NotesState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getNotesByTitleOrContent(filter: String) {
        Timber.e("Poziv metode")
        publishSubject.onNext(filter)
    }

    override fun deleteAllNotes() {
        val subscription = noteRepository
            .deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("DELETED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertNote(entity: Note) {
        val subscription = noteRepository
            .insert(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("INSERTED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertAllNotes(entities: List<Note>) {
        val subscription = noteRepository
            .insertAll(entities)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("INSERTED STUDENTS WITH IDs:  $it")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateNote(id: Int, title: String, content: String, archieved: Boolean, date: String) {
        val subscription = noteRepository
            .updateNoteById(id, title, content, archieved, date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("UPDATED")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteNote(id: Int) {
        val subscription = noteRepository
            .deleteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Deleted")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    // TODO: not working
    override fun getFiveLastDaysNote(startStats: (input: IntArray) -> Unit) {
        var bool = false
        bool = true

        val currDate: LocalDate = LocalDate.parse(LocalDateTime.now().minusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val subscription = noteRepository
            .getAll2()
            /*.forEach{
            Timber.e("FiveLastDaysInner")
            var daysCounter: IntArray = intArrayOf(0,0,0,0,0)
            it.forEach {
                val date = LocalDate.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                val period = Period.between(currDate, date)

                Timber.e("Comparing " + period.days)
                if( period.days >= 0 && period.days < 5){
                    Timber.e("Adding day " + (4-period.days))
                    daysCounter[4-period.days] = daysCounter[4-period.days]+1
                    Timber.e("After " + daysCounter[4-period.days])
                }

            }
            // Error lose ih ubacuje van if-a
            Timber.e("Called " + daysCounter[0] + " " + daysCounter[1] + " " + daysCounter[2] + " " + daysCounter[3] + " " + daysCounter[4])
            startStats(daysCounter)
        }*/
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("FiveLastDaysInner")
                    var daysCounter: IntArray = intArrayOf(0,0,0,0,0)
                    it.forEach {
                        val date = LocalDate.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        val period = Period.between(currDate, date)

                        Timber.e("Comparing " + period.days)
                        if( period.days >= 0 && period.days < 5){
                            Timber.e("Adding day " + (4-period.days))
                            daysCounter[4-period.days] = daysCounter[4-period.days]+1
                            Timber.e("After " + daysCounter[4-period.days])
                        }

                    }
                    // Error lose ih ubacuje van if-a
                    Timber.e("Called " + daysCounter[0] + " " + daysCounter[1] + " " + daysCounter[2] + " " + daysCounter[3] + " " + daysCounter[4])
                    startStats(daysCounter)
                },
                {
                    notesState.value = NotesState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getArchievedNotes() {
        val subscription = noteRepository
            .getArchievedNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    notesState.value = NotesState.Success(it)
                },
                {
                    notesState.value = NotesState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}